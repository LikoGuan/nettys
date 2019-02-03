package com.taobao.rainbow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by likoguan on 30/01/19.
 */
public class DefaultEventCenter implements EventCenter, Runnable {

    public static final DefaultEventCenter defaultCenter = new DefaultEventCenter();

    private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private final PriorityBlockingQueue<TimeoutFuture> queue = new PriorityBlockingQueue<>();

    private final ThreadPoolExecutor executorPool = new ThreadPoolExecutor(20, 20, 10, TimeUnit.MINUTES, taskQueue);

    private Thread observer;

    private volatile boolean running;


    public DefaultEventCenter() {
        super();
        observer = new Thread(this);
        observer.start();
        running = true;
    }

    @Override
    public EventEmit getEmit(final Object target) {
        return new EventEmit() {
            private final Map<EventName, EventListener> listeners = new HashMap<>();

            @Override
            public void on(EventName eventName, EventListener eventListener) {
                EventListener listener = listeners.get(eventName);
                if (listener == null) {
                    listeners.put(eventName, eventListener);
                } else if (listener instanceof BatchEventListener) {
                    BatchEventListener batchEventListener = (BatchEventListener) listener;
                    EventListener newEventListener = batchEventListener.inc(eventListener);
                    listeners.put(eventName, newEventListener);
                } else {
                    listeners.put(eventName, new BatchEventListener(new EventListener[] {listener, eventListener}));
                }
            }

            @Override
            public void remove(EventName eventName, EventListener eventListener) {
                EventListener listener = listeners.get(eventName);
                if (listener != null) {
                    if (listener instanceof BatchEventListener) {
                        BatchEventListener batchEventListener = (BatchEventListener) listener;
                        EventListener newListener = batchEventListener.dec(eventListener);
                        listeners.put(eventName, newListener);
                    } else {
                        if (listener.equals(eventListener)) {
                            this.removeAll(eventName);
                        }
                    }
                }
            }

            @Override
            public void removeAll(EventName eventName) {
                listeners.remove(eventName);
            }

            //在当前线程中fire
            @Override
            public void fire(EventName eventName, Object... objects) {
                EventListener eventListener = listeners.get(eventName);
                if (eventListener != null) {
                    Event event = new Event(eventName, target, objects, System.currentTimeMillis());
                    try {
                        eventListener.onFire(event);
                    } catch (Throwable e) {
                        //log
                    }
                }
            }

            //提交到线程池中去fire
            @Override
            public void notify(EventName eventName, Object... objects) {
                final EventListener eventListener = listeners.get(eventName);
                if (eventListener != null) {
                    final Event event = new Event(eventName, target, objects, System.currentTimeMillis());
                    try {
                        executorPool.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    eventListener.onFire(event);
                                } catch (Throwable e) {
                                    //log
                                } finally {
                                    //log module
                                }
                            }
                        });
                    } catch (Throwable e) {
                        //log 提交任务失败
                    }
                }
            }

            @Override
            public TimeoutFuture setTimeout(long timeout, final Runnable code) {

                TimeoutFuture timeoutFuture = new TimeoutFuture() {

                    private long timeout = 0;
                    private long fireTime = 0;
                    private AtomicBoolean enable = new AtomicBoolean(false);

                    @Override
                    public void setTimeout(long timeout) {
                        this.fireTime = System.currentTimeMillis() + timeout;
                        this.timeout = timeout;
                        if (timeout <= 0) {
                            if (enable.compareAndSet(false, true)) {
                                this.fire();
                            }
                        } else {
                            this.cancel();
                            if (enable.compareAndSet(false, true)) {
                                queue.add(this);
                                TimeoutFuture future = queue.peek();
                                if (future != null && future.getFireTime() >= this.getFireTime()) {
                                    observer.interrupt();
                                }
                            }
                        }
                    }

                    @Override
                    public long getTimeout() {
                        return this.timeout;
                    }

                    @Override
                    public long getFireTime() {
                        return this.fireTime;
                    }

                    @Override
                    public void cancel() {
                        if (enable.compareAndSet(true, false)) {
                            queue.remove(this);
                        }
                    }

                    @Override
                    public void fire() {
                        if (enable.compareAndSet(true, false)) {
                            try {
                                executorPool.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        code.run();
                                    }
                                });
                            } catch (Throwable e) {
                                //log
                            }
                        }
                    }

                    @Override
                    public int compareTo(TimeoutFuture o) {
                        return (int) (this.getTimeout() - o.getTimeout());
                    }
                };

                timeoutFuture.setTimeout(timeout);
                return timeoutFuture;
            }
        };
    }

    @Override
    public void run() {
        long sleepTime = 0;
        while (running) {
            TimeoutFuture timeoutFuture = queue.peek();
            if (timeoutFuture != null) {
                if (timeoutFuture.getFireTime() < System.currentTimeMillis()) {
                    timeoutFuture = queue.poll();
                    if (timeoutFuture != null) {
                        timeoutFuture.fire();
                    }
                    sleepTime = 0;
                } else {
                    sleepTime = Math.max(timeoutFuture.getFireTime() - System.currentTimeMillis(), 1000L);
                    //最少休眠1秒，防止大量毫秒差别的任务引起大量线程切换
                }
            } else {
                sleepTime = 30000L;//30秒必醒来一次
            }

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    sleepTime = 0;
                    //do next loop
                }
            }
        }
    }


    private static class BatchEventListener implements EventListener {

        final EventListener[] listeners;

        public BatchEventListener(EventListener[] listeners) {
            this.listeners = listeners;
        }

        @Override
        public void onFire(Event event) {
            for (EventListener listener : listeners) {
                try {
                    listener.onFire(event);
                } catch (Throwable e) {
                    //log
                }
            }
        }

        public EventListener inc(EventListener listener) {
            EventListener[] newListeners = new EventListener[this.listeners.length + 1];
            System.arraycopy(listeners, 0, newListeners, 1, listeners.length);
            newListeners[0] = listener;
            return new BatchEventListener(newListeners);
        }

        public EventListener dec(EventListener listener) {
            int remove = -1;
            for (int i=0; i<this.listeners.length; i++) {
                EventListener el = this.listeners[i];
                if (el != null && el.equals(listener)) {
                    remove = i;
                    break;
                }
            }
            if (remove >= 0) {
                EventListener[] newListeners = new EventListener[this.listeners.length - 1];
                if (remove > 0) {
                    System.arraycopy(this.listeners, 0, newListeners, 0, remove);
                }
                int endLen = newListeners.length - remove;
                if (endLen > 0) {
                    System.arraycopy(this.listeners, remove+1, newListeners, remove, endLen);
                }
                if (newListeners.length == 1) {
                    return newListeners[0];
                } else {
                    return new BatchEventListener(newListeners);
                }
            }
            return this;
        }
    }
}
