package com.taobao.rainbow;

import java.io.Serializable;

/**
 * Created by admin on 2019/1/30.
 */
public class RainbowInfo implements Serializable {
    public final String clientIp;
    public final int clientPort;
    public final int clientCode;
    public final String deviceId;
    public final String osName;
    public final String osVersion;
    public final String appVersion;
    public final String desKey;

    public RainbowInfo(String clientIp,
                       int clientPort,
                       int clientCode,
                       String deviceId,
                       String osName,
                       String osVersion,
                       String appVersion,
                       String desKey) {
        this.clientIp = clientIp;
        this.clientPort = clientPort;
        this.clientCode = clientCode;
        this.deviceId = deviceId;
        this.osName = osName;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.desKey = desKey;
    }
}
