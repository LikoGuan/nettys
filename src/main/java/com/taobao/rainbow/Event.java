package com.taobao.rainbow;

/**
 * Created by likoguan on 30/01/19.
 */
public class Event {
    //事件激发者
    private Object target;

    //事件激发上下文
    private Object[] eventObj;

    //事件名称
    private EventName eventName;

    //事件激发时间
    private long fireTime;

    public Event(EventName eventName, Object target, Object[] eventObj, long fireTime) {
        this.eventName = eventName;
        this.target = target;
        this.eventObj = eventObj;
        this.fireTime = fireTime;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object[] getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object[] eventObj) {
        this.eventObj = eventObj;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        this.eventName = eventName;
    }

    public long getFireTime() {
        return fireTime;
    }

    public void setFireTime(long fireTime) {
        this.fireTime = fireTime;
    }
}
