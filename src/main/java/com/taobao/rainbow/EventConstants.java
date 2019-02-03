package com.taobao.rainbow;

/**
 * Created by likoguan on 30/01/19.
 */
public class EventConstants {
    //链接收到Packet
    public final static EventName BEFORE_MESSAGE = new EventName("beforeMessage");
    //链接发送消息
    public final static EventName BEFORE_SEND = new EventName("beforeSend");
    //链接收到Packet
    public final static EventName ON_MESSAGE = new EventName("onMessage");
    //链接收到握手Packet
    public final static EventName ON_SHAKE = new EventName("onShake");
    //链接发送消息
    public final static EventName ON_SEND = new EventName("onSend");
    //用户路由bind
    public final static EventName USER_ROUTER_PUBLISH = new EventName("userRouterPublish");
    //用户路由unbind
    public final static EventName USER_ROUTER_UNPUBLISH = new EventName("userRouterUnPublish");
    //一个连接连上
    public final static EventName CONNECT = new EventName("connect");
    //一个连接断开
    public final static EventName DISCONNECT = new EventName("disconnect");
    //clientCode变动
    public final static EventName ON_CLIENT_CODE_CHANGE = new EventName("clientCodeChange");
    //连接处理发生错误
    public final static EventName ERROR = new EventName("error");
    //超时
    public final static EventName TIMEOUT = new EventName("timeout");
    //manager周期开始
    public final static EventName MANAGER_CONN_BEGIN = new EventName("managerConnBegin");
    //manager周期执行
    public final static EventName ON_MANAGER_CONN = new EventName("onManagerConn");
    //manager周期结束
    public final static EventName MANAGER_CONN_END = new EventName("managerConnEnd");
    //广播事件
    public final static EventName ON_BROADCAST = new EventName("onBroadcast");
}
