package com.taobao.rainbow;

/**
 * Created by likoguan on 2/02/19.
 */
public class RouterInfoConfiguration {
    public int[] clientCodes;
    public String extremity;
    public int cacheTime = -1;
    public int offlineCacheTime = -1;
    public int maxSize = -1;
}

//一个extremity可以对应多个clientCode，比如移动端可以对应android和ios ？