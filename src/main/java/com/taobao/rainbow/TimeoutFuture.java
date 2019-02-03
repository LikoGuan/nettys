package com.taobao.rainbow;

/**
 * Created by likoguan on 30/01/19.
 */
public interface TimeoutFuture extends Comparable<TimeoutFuture> {
    void setTimeout(long timeout);

    long getTimeout();

    long getFireTime();

    void cancel();

    void fire();
}
