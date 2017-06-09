package com.etongwl.volleyutils.net;


/**
 * 请求接口返回内容监听
 * Created by jason on 16/3/15.
 */
public interface ResponseResultListenner {
    /**
     * 请求成功回调
     * @param resultObj
     */
    public void success(Object resultObj);

    /**
     * 请求失败回调
     * @param failCode
     * @param failMsg
     */
    public void faild(Object failCode, Object failMsg);
}
