package com.etongwl.volleyutils;

import java.util.HashMap;

/**
 * 类描述：网络请求配置
 * 创建人：sarahstone
 * 创建时间：2016/6/12 11:16
 * 修改备注：
 */
public abstract class HttpConfig {
    //url存储
    public static HashMap<String, String> urls ;

    private HttpConfig(){
         urls = new HashMap<>();
    }

    /**
     * 例如
     * //登录urls.put("login", requestUrl + "/mdcom/mobile/mobileUsers/login.jhtml");
     * @return
     */
    public  abstract  void setUrlByCode();

    /**
     * 获取URLbyCode
     * @return
     */
    public static HashMap<String,String> getUrlByCode(){
        return urls;
    }
}
