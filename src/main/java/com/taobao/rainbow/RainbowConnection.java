package com.taobao.rainbow;

/**
 * Created by admin on 2019/1/30.
 */
public interface RainbowConnection {
    //准备状态
    public static final int STATUS_READY = 0;
    //已经连接
    public static final int STATUS_CONNECT = 1;
    //通过认证
    public static final int STATUS_AUTHENTICATION = 2;
    //关闭
    public static final int STATUS_CLOSE = 3;

    public int getConnectionId();

    public void send(Packet packet);

    public void close(Packet packet);

    public boolean isClosed();

    public boolean getStatus();

    public <T> T getConnectionProperty(String key, Class<T> clazz);

    public void setConnectionProperty(String key, Object value);

    public RainbowInfo getConnectionInfo();

    public void setRainbowInfo(RainbowInfo info);

    public EventEmit getServerEventEmit();

    public long getConnectionGmtTime();

    public long getConnectionLastSenderTime();

    public long getConnectionLastReceiveTime();

    public long getCheckTime();

    public void setCheckTime(long checkTime);
}
