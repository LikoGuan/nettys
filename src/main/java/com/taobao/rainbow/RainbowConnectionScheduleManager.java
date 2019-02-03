package com.taobao.rainbow;

/**
 * Created by likoguan on 31/01/19.
 */
public interface RainbowConnectionScheduleManager {
    ScheduleFuture<RainbowConnection> getFuture(RainbowConnection connection);

    void onScheduleStart(EventListener listener);

    void onSchedule(EventListener listener);

    void onScheduleEnd(EventListener listener);
}
