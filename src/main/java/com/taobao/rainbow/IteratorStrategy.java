package com.taobao.rainbow;

/**
 * Created by likoguan on 31/01/19.
 */
public interface IteratorStrategy extends IConnectionIterator {
    //是否取消
    boolean isCancel();
    //下一轮qps
    int getQPS();
    //每个周期开始，都会执行该方法
    void onNextTick();
    //执行完成（被取消也会执行）
    void onDone();
}
