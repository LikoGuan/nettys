package com.taobao.rainbow;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by likoguan on 31/01/19.
 */
public class NettyRainbowServer implements RainbowServer {
    public static boolean isApartShake = false;

    private AtomicInteger clientHeartBeatTime = new AtomicInteger();

    private NettyRainbowConnectionManager manager;

    private EventEmit emit = DefaultEventCenter.defaultCenter.getEmit(this);

    @Override
    public void on(EventName eventName, EventListener eventListener) {
        emit.on(eventName, eventListener);
    }

    @Override
    public void remove(EventName eventName, EventListener eventListener) {
        emit.remove(eventName, eventListener);
    }

    @Override
    public void removeAll(EventName eventName) {
        emit.removeAll(eventName);
    }

    @Override
    public void fire(EventName eventName, Object... objects) {
        emit.fire(eventName, objects);
    }

    @Override
    public void notify(EventName eventName, Object... objects) {
        emit.notify(eventName, objects);
    }

    @Override
    public TimeoutFuture setTimeout(long timeout, Runnable code) {
        return emit.setTimeout(timeout, code);
    }

    @Override
    public void onMessage(EventListener listener) {
        emit.on(EventConstants.ON_MESSAGE, listener);
    }

    @Override
    public void onShake(EventListener listener) {
        emit.on(EventConstants.ON_SHAKE, listener);
    }

    @Override
    public void onConnected(EventListener listener) {
        emit.on(EventConstants.CONNECT, listener);
    }

    @Override
    public void onDisConnected(EventListener listener) {
        emit.on(EventConstants.DISCONNECT, listener);
    }

    @Override
    public void onError(EventListener listener) {
        emit.on(EventConstants.ERROR, listener);
    }

    @Override
    public void onTimeout(EventListener listener) {
        emit.on(EventConstants.TIMEOUT, listener);
    }

    @Override
    public void start() {
        //初始化服务器
        this.initServer();

        //防止disconnect不执行逻辑
        manager.onSchedule(new EventListener() {
            @Override
            public void onFire(Event event) {
                RainbowConnection conn = (RainbowConnection) event.getEventObj()[0];
                ScheduleFuture task = (ScheduleFuture) event.getEventObj()[1];
                if (conn.isClosed()) {
                    //destroyConnection((NettyRainbowConnection)conn, task);
                }
            }
        });
    }

    @Override
    public void close() {
        //bootstrap.releaseExternalResources();
    }

    private void initServer() {
        //bootstrap = new ServerBootstrap(...);
        //bootstrap.setOption("backlog", 1024); 半连接队列直接决定了QPS ?????????

        /*
        final PacketEncoder encoder = new PacketEncoder();
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", encoder);
        pipeline.addLast("handle", new NettyRainbowServerHandler());
         */

        //bootstrap.bind(...);
    }

    /*
    public class NettyRainbowServerHandler extends SimpleChannelUpstreamHandler {
        private final NettyRainbowConnection connection = new NettyRainbowConnection();

        private final ScheduleFuture task = manager.getFuture(connection);

        public NettyRainbowServerHandler() {
            connection.serverEmit = emit;
        }
    }
    */
}
