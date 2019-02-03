package com.taobao.rainbow;

/**
 * Created by likoguan on 1/02/19.
 */
public abstract class BizHandler<T> implements ParseMsgStage {

    public abstract void handler(RainbowConnection connection, T request, Packet packet);

    protected boolean isSynchronousEachConnection() {
        return false;
    }

    protected static Packet getResponsePacket(Packet packet, byte[] rsp) {
        Packet p = new Packet();
        p.setVersion(packet.getVersion());
        p.setMsgType(packet.getMsgType());
        p.setCheckCode(0);
        p.setSessionId(packet.getSessionId());
        p.setData(rsp);
        p.setFlags((byte) 0);
        return p;
    }
}
