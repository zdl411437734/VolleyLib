package com.etongwl.volleyutils.net;



/**
 * 请求接口返回内容监听
 * Created by jason on 16/3/15.
 */
public interface ItemResultListenner {
    /**
     * 请求成功回调
     *
     * @param json
     */
    void success(String json);

//    /**
//     * 请求失败回调
//     *
//     * @param failCode
//     * @param failMsg
//     */
//    void faild(Object failCode, Object failMsg);

    /**
     * 请求失败回调
     */
    void httpfaild();
}
