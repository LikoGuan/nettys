package com.taobao.rainbow;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by likoguan on 31/01/19.
 */
public class NettyRainbowConnectionManager implements Runnable, RainbowConnectionManager, RainbowConnectionScheduleManager {
    private static final long tickDuration = 1;
    private static final Object value = new Object();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private ConcurrentHashMap<DefaultScheduleFuture, Object>[] wheel;
    private volatile int tick;
    private EventEmit emit = DefaultEventCenter.defaultCenter.getEmit(this);

    public NettyRainbowConnectionManager() {
        int ticksPerWheel = (int) ((Constants.MAX_HEART_BEAT_TIME - 5*1000) / (tickDuration*1000));
        scheduledExecutorService.scheduleAtFixedRate(this, tickDuration, tickDuration, TimeUnit.SECONDS);
        wheel = new ConcurrentHashMap[ticksPerWheel];
        int f = 20*10000 / ticksPerWheel;
        for (int i=0; i<ticksPerWheel; i++) {
            wheel[i] = new ConcurrentHashMap<DefaultScheduleFuture, Object>(f);
        }
        this.tick = 0;
    }

    @Override
    public void forEach(IConnectionIterator iterator) {
        int index = 0;
        for (int i=0; i<wheel.length; i++) {
            for (DefaultScheduleFuture future : wheel[i].keySet()) {
                try {
                    RainbowConnection conn = future.connection;
                    if (conn != null) {
                        iterator.iterator(index, conn);
                        index++;
                    }
                } catch (Exception e) {
                    //log
                }
            }
        }
    }

    @Override
    public void forEach(IteratorStrategy strategy) {
        final DelayIterator delayIterator = new DelayIterator();
        delayIterator.strategy = strategy;
        emit.setTimeout(EventEmit.EXECUTE_NOW, delayIterator);
    }

    @Override
    public void onBroadcast(EventListener listener) {
        emit.on(EventConstants.ON_BROADCAST, listener);
    }

    @Override
    public void broadcast(IteratorStrategy strategy) {
        final BroadcastDelayIterator iterator = new BroadcastDelayIterator();
        iterator.strategy = strategy;
        emit.fire(EventConstants.ON_BROADCAST, iterator, new Long(-1));
    }

    @Override
    public ScheduleFuture<RainbowConnection> getFuture(RainbowConnection connection) {
        ScheduleFuture task = new DefaultScheduleFuture(connection);
        return task;
    }

    @Override
    public void onScheduleStart(EventListener listener) {
        emit.on(EventConstants.MANAGER_CONN_BEGIN, listener);
    }

    @Override
    public void onSchedule(EventListener listener) {
        emit.on(EventConstants.ON_MANAGER_CONN, listener);
    }

    @Override
    public void onScheduleEnd(EventListener listener) {
        emit.on(EventConstants.MANAGER_CONN_END, listener);
    }

    @Override
    public void run() {
        final Thread currentThread = Thread.currentThread();
        try {
            currentThread.setName("RainbowTimer-Schedule-Worker");
        } catch (SecurityException e) {
            //log
        }

        final int nowTick = this.tick;

        emit.fire(EventConstants.MANAGER_CONN_BEGIN);

        //提交任务
        scheduledExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                final Thread currentThread = Thread.currentThread();
                try {
                    currentThread.setName("RainbowTimer-Task-Worker");
                } catch (SecurityException e) {
                    //log
                }

                long startTime = System.currentTimeMillis();
                int count = 0;
                int executeCount = 0;
                try {
                    ConcurrentHashMap<DefaultScheduleFuture, Object> map = wheel[nowTick];
                    LinkedList<DefaultScheduleFuture> removeList = new LinkedList<>();
                    for (DefaultScheduleFuture task : map.keySet()) {
                        RainbowConnection connection = task.connection;
                        count++;
                        if (nowTick != task.taskTick) {
                            removeList.add(task);
                        } else {
                            try {
                                if (connection != null && !connection.isClosed()) {
                                    emit.fire(EventConstants.ON_MANAGER_CONN, connection, task);
                                    executeCount ++;
                                }
                            } catch (Exception e) {
                                //log
                            }
                        }
                    }

                    if (removeList.size() > 0) {
                        for (DefaultScheduleFuture task : removeList) {
                            task.stop();
                        }
                    }
                } catch (Exception e) {
                    //log
                } finally {
                    tick = (nowTick + 1 >= wheel.length) ? 0 : nowTick+1;//转向下一轮
                }
            }
        });

        emit.fire(EventConstants.MANAGER_CONN_END);
    }

    public class DefaultScheduleFuture implements ScheduleFuture<RainbowConnection> {
        int taskTick = -1;
        RainbowConnection connection;

        public DefaultScheduleFuture(RainbowConnection connection) {
            this.connection = connection;
        }

        @Override
        public synchronized void submit() {
            if (this.taskTick < 0 && this.connection != null) {
                int nowTick = tick;
                this.taskTick = (nowTick - 1 < 0) ? wheel.length-1 : nowTick-1;
                wheel[this.taskTick].put(this, value);
            }
        }

        @Override
        public synchronized void stop() {
            if (this.taskTick >= 0) {
                wheel[this.taskTick].remove(this);
            }
            this.connection = null;
            this.taskTick = -1;
        }

        @Override
        public RainbowConnection getTarget() {
            return connection;
        }
    }

    public class DelayIterator implements Runnable {
        public int cur = wheel.length - 1;
        public IteratorStrategy strategy;
        public Iterator<DefaultScheduleFuture> iter;
        public int index = 0;

        public boolean exe(int size) {
            if (iter == null) {
                iter = wheel[cur].keySet().iterator();
            }

            while (iter.hasNext()) {
                if (strategy.isCancel() || size <= 0) {
                    return false;
                }

                DefaultScheduleFuture future = iter.next();
                strategy.iterator(index, future.connection);
                index++;
                if (index % size == 0) {//表示下一轮需要进行
                    return true;
                }
                if (index > 300000) {//保护如果大于30w，打warn log
                    return false;
                }
            }

            if (cur == 0) {
                return false;
            } else {
                cur--;
                iter = null;
                return true;
            }
        }

        @Override
        public void run() {
            long time = System.currentTimeMillis();
            strategy.onNextTick();//每个周期开始执行
            int qps = strategy.getQPS();//获取qps
            if (exe(qps)) {
                afterEachLoop(time);
            } else {
                strategy.onDone();
            }
        }

        public void afterEachLoop(long loopStartTime) {
            long less = 1000L - (System.currentTimeMillis() - loopStartTime);
            emit.setTimeout(less, this);
        }
    }

    public class BroadcastDelayIterator extends DelayIterator {
        public void afterEachLoop(long loopStartTime) {
            long less = 1000L - (System.currentTimeMillis() - loopStartTime);
            emit.fire(EventConstants.ON_BROADCAST, this, new Long(less));
        }
    }
}
