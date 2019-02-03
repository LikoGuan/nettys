package com.taobao.rainbow;

/**
 * Created by likoguan on 2/02/19.
 */
public interface IUserRouterCenter<T> {
    //发布路由信息
    T publish(Long userId, T connection) throws Exception;

    //注销路由信息
    boolean unPublish(Long userId, T connection) throws Exception;

    //获取用户路由
    T getRouterByUserId(Long userId) throws Exception;

    //统计
    int size();

    //router的端名
    String getExtremity();
}
