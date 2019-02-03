package com.taobao.rainbow;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2019/1/30.
 */
public class Packet {
    private static final AtomicInteger seq = new AtomicInteger();

    private byte version;

    private byte msgType;

    private int sessionId = seq.incrementAndGet();

    private byte flags;

    private int checkCode;

    private byte[] data;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public int getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(int checkCode) {
        this.checkCode = checkCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength() {
        return data == null ? 0 : data.length;
    }

    public void setFlagsBitValue(int nPos, boolean nValue) {
        if (nValue) {
            flags |= (1 << nPos);
        } else {
            flags &= ~(1 << nPos);
        }
    }

    public boolean getFlagsBitValue(int nPos) {
        return (flags & (1 << nPos)) != 0;
    }
}
