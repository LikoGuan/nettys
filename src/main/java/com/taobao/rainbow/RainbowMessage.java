package com.taobao.rainbow;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by likoguan on 3/02/19.
 */
public class RainbowMessage implements Serializable {
    public int messageId;
    public byte rainbowType;
    BasePackage basePackage;

    public <T> T getRequest(Class<T> clazz) {
        if (basePackage == null || basePackage.bizObject == null) {
            return null;
        }
        //兼容
        if (basePackage.bizObject instanceof byte[]) {
            return JSON.parseObject(new String((byte[])basePackage.bizObject, "UTF-8"), clazz);
        }
        return (T) basePackage.bizObject;
    }

    public Object getRequest() {
        return basePackage == null ? null : basePackage.bizObject;
    }

    public void setRequest(Object obj) {
        if (basePackage == null) {
            basePackage = new BasePackage();
        }
        basePackage.bizObject = obj;
    }

    public void setAttachment(String key, Object attr) {
        if (basePackage == null) {
            basePackage = new BasePackage();
        }
        if (basePackage.attachment == null) {
            basePackage.attachment = new HashMap<String, Object>();
        }
        basePackage.attachment.put(key, attr);
    }

    public Object getAttachment(String key) {
        if (basePackage == null || basePackage.attachment == null) {
            return null;
        }
        return basePackage.attachment.get(key);
    }
}
