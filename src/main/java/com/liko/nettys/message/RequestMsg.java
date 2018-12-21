package com.liko.nettys.message;

/**
 * Created by likoguan on 7/12/18.
 */
public class RequestMsg {
    private long no;

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "RequestMsg[no=" + this.no + "]";
    }
}
