package com.taobao.rainbow;

import java.nio.charset.Charset;

/**
 * Created by likoguan on 31/01/19.
 */
public class Constants {
    public static final byte[] MAGIC_NUM = {66, 88};

    public static final int HEADER_LEN = 17;

    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static final byte CURRENT_VERSION = 0;

    public static final byte MSG_TYPE_HEART_BEAT = 0;

    public static final byte MSG_TYPE_CLOSE = Byte.MAX_VALUE;

    public static final int MAX_HEART_BEAT_TIME = 90*1000;

    public static final int MAX_PACKET_SIZE = 10*1024*1024;

    public static final String CON_USER_LOG_KEY = "100000122";

    public static final int CON_USER_LOG_OPERTYPE = 4;
}
