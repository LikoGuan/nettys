package com.taobao.rainbow;

/**
 * Created by likoguan on 30/01/19.
 */
public interface EventEmit {
    static final int EXECUTE_NOW = -1;

    void on(EventName eventName, EventListener eventListener);

    void remove(EventName eventName, EventListener eventListener);

    void removeAll(EventName eventName);

    void fire(EventName eventName, Object... objects);

    void notify(EventName eventName, Object... objects);

    //如果timeout小于0，立马提交异步执行
    TimeoutFuture setTimeout(long timeout, Runnable code);
}
