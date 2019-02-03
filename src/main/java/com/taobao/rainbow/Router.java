package com.taobao.rainbow;

/**
 * Created by likoguan on 2/02/19.
 */
public class Router {
    public final RainbowConnection connection;
    public final ClientRouterInfo router;

    public Router(RainbowConnection conn, ClientRouterInfo router) {
        this.connection = conn;
        this.router = router;
    }
}
