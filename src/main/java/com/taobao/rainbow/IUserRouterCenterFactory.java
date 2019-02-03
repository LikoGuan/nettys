package com.taobao.rainbow;

/**
 * Created by likoguan on 2/02/19.
 */
public interface IUserRouterCenterFactory {
    IUserRouterCenter<Router> getRouterCenterByExtremity(String extremity);
    IUserRouterCenter<Router> getRouterCenterByClientCode(int clientCode);
    IUserRouterCenter<Router>[] getAllRouter();
    String getExtremityByClientCode(int clientCode);
}
