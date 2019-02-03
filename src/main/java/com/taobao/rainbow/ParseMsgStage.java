package com.taobao.rainbow;

/**
 * Created by likoguan on 1/02/19.
 */
public interface ParseMsgStage {
    Object parse(Packet packet);
    boolean canParse(byte msgType);
}
