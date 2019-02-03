package com.taobao.rainbow;

/**
 * Created by admin on 2019/1/30.
 */
public interface RainbowServer extends EventEmit {
    //注册有效数据传上来监听器
    public void onMessage(EventListener listener);
    //注册有握手请求传上来监听器
    public void onShake(EventListener listener);
    //注册链接连上监听器
    public void onConnected(EventListener listener);
    //注册链接关闭监听器
    public void onDisConnected(EventListener listener);
    //注册链接网络IO发送错误监听器
    public void onError(EventListener listener);
    //注册网络超时监听器
    public void onTimeout(EventListener listener);

    public void start();

    public void close();
}
