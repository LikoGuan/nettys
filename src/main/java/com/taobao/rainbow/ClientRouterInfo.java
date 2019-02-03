package com.taobao.rainbow;

import java.io.Serializable;

/**
 * Created by likoguan on 2/02/19.
 */
public class ClientRouterInfo implements Serializable {
    private String appVersion;
    private String ip;
    private String sysName;
    private transient String extremity;

    public ClientRouterInfo() {

    }

    public ClientRouterInfo(String appVersion, String ip, String sysName) {
        this.appVersion = appVersion;
        this.ip = ip;
        this.sysName = sysName;
    }

    public ClientRouterInfo(String appVersion, String ip, String sysName, String extremity) {
        this(appVersion, ip, sysName);
        this.extremity = extremity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientRouterInfo that = (ClientRouterInfo) o;
        if (appVersion != null ? !appVersion.equals(that.appVersion) : that.appVersion != null) {
            return false;
        }
        if (extremity != null ? !extremity.equals(that.extremity) : that.extremity != null) {
            return false;
        }
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) {
            return false;
        }
        if (sysName != null ? !sysName.equals(that.sysName) : that.sysName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = appVersion != null ? appVersion.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (sysName != null ? sysName.hashCode() : 0);
        result = 31 * result + (extremity != null ? extremity.hashCode() : 0);
        return result;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getExtremity() {
        return extremity;
    }

    public void setExtremity(String extremity) {
        this.extremity = extremity;
    }
}
