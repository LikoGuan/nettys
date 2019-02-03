package com.taobao.rainbow;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by likoguan on 1/02/19.
 */
public class BizConstants extends Constants {

    public static final String SIGN_HMAC = "hmac";
    public static final String PROTOCOL_SIGN = "sign";
    public static final String PROTOCOL_SIGN_METHOD = "sign_method";

    //top批量接口path
    public static final String TOP_BATCH_PATH = "/router/batch";
    //客户端上行ping包
    public static final byte MSG_TYPE_CLIENT_HEART_BEAT = 8;
    //连接握手
    public static final byte MSG_TYPE_SHAKE_HANDS = 0;
    //API请求
    public static final byte MSG_TYPE_JDY_API_REQUEST = 1;
    //绑定
    public static final byte MSG_BIND_USER = 2;
    //TPN push消息
    public static final byte MSG_TYPE_PUSH = TpnPushSender.MSG_TYPE_PUSH;
    //连接被踢出
    public static final byte MSG_USER_KICKED_OUT = 4;
    //可被复用的连接token
    public static final byte MSG_TYPE_REUSABLE_SHAKE_HANDS = 5;
    //程序控制命令
    public static final byte MSG_TYPE_PUSH_CMD = CmdPushSender.MSG_TYPE_PUSH_CMD;
    //TPN推送业务消息变更（面向程序）
    public static final byte MSG_TYPE_PUSH_NOTIFY = 7;
    //50以上提供给业务方接入
    public static final byte UNIVERSAL_BEGIN = 50;
    //连接结束的通知
    public static final byte MSG_CLOSE = Byte.MAX_VALUE;

    //业务错误码
    public static final int SUCCESS = 0;
    public static final int MSG_BIND_USER_ERROR = 1000;

    /*
    以下为链接property key
     */
    //当前绑定的用户信息
    public static final String CONNECTION_USER_INFO = "C_user_info";

    public static final AtomicInteger seq = new AtomicInteger();
}
