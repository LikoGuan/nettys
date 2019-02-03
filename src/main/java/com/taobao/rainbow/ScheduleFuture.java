package com.taobao.rainbow;

/**
 * Created by likoguan on 31/01/19.
 */
public interface ScheduleFuture<T> {
    void submit();

    void stop();

    T getTarget();
}
