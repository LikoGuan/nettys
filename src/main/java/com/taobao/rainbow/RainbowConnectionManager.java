package com.taobao.rainbow;

/**
 * Created by likoguan on 31/01/19.
 */
public interface RainbowConnectionManager {
    /*
    一次性遍历所有连接
     */
    void forEach(IConnectionIterator iterator);

    /*
    延迟遍历所有连接，strategy是遍历策略
     */
    void forEach(IteratorStrategy strategy);

    /*
    注册广播遍历连接的事件
     */
    void onBroadcast(EventListener listener);

    /*
    广播遍历连接
     */
    void broadcast(IteratorStrategy strategy);
}
