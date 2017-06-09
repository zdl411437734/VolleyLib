package com.etongwl.volleyutils.net;

import android.content.Context;

import com.etongwl.volleyutils.HttpConfig;
import com.etongwl.volleyutils.Log;
import com.etongwl.volleyutils.VolleyUtils;

import java.util.Map;

/**
 * 类描述：Http请求工具类
 * 创建人：sarahstone
 * 创建时间：2016/6/15 17:32
 * 修改备注：
 */
public class HttpRequestManager {

    /**
     * 私有构造方法
     */
    private HttpRequestManager() {

    }

    /**
     * 构造使用单例模式
     */
    public static HttpRequestManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 实现了线程安全，又避免了同步带来的性能影响
     */
    private static class LazyHolder {
        private static final HttpRequestManager INSTANCE = new HttpRequestManager();
    }

    /**
     * post请求
     *
     * @param context   上下文
     * @param urlCode   请求地址code
     * @param tag       标注用来取消请求
     * @param params    请求参数
     * @param listenner 请求回调
     */
    public void post(final Context context, String urlCode, final String tag,
                     Map<String, String> params, final ItemResultListenner listenner) {
        sendPost(context, urlCode, tag, params, listenner, true);
    }

    /**
     * post请求
     *
     * @param context   上下文
     * @param urlCode   请求地址code
     * @param tag       标注用来取消请求
     * @param params    请求参数
     * @param listenner 请求回调
     * @param isCache   是否走缓存
     */
    public void post(final Context context, String urlCode, final String tag,
                     Map<String, String> params, final ItemResultListenner listenner, boolean isCache) {
        sendPost(context, urlCode, tag, params, listenner, isCache);
    }

    /**
     * Post请求
     *
     * @param tag       标注用来取消请求的
     * @param params    请求的参数
     * @param listenner 回调监听
     * @param isCache   是否走缓存
     */
    private void sendPost(final Context context, String urlCode, final String tag, Map<String,
            String> params, final ItemResultListenner listenner, boolean isCache) {
        try {
            String url = HttpConfig.getUrlByCode().get(urlCode);
            Log.writeSystemLog(tag + " request url:" + url);
            Log.writeSystemLog(tag + " request params:" + params.toString());
            VolleyUtils.getInstance(context).volleyPost(url, tag, params, new ResponseResultListenner() {
                @Override
                public void success(Object resultObj) {
                    Log.writeSystemLog(tag + " request success,and return：" + resultObj.toString());
                    listenner.success(resultObj.toString());
                }

                @Override
                public void faild(Object failCode, Object failMsg) {
                    //MyToast.getInstance().showToast(context, "网络异常，请稍后再试");
                    Log.writeSystemLog(tag + " request faild：" + failCode.toString());
                    listenner.httpfaild();
                }
            }, isCache);
        } catch (Exception e) {
            if (null != listenner) {
                listenner.httpfaild();
            }
            e.printStackTrace();
        }

    }

    /**
     * Get请求
     *
     * @param context   上下文
     * @param url       请求地址
     * @param tag       标注用来取消时使用
     * @param listenner 回调
     */
    public void get(Context context, String url, String tag, ItemResultListenner listenner) {
        sendGet(context, url, tag, listenner, true);
    }

    /**
     * Get请求
     *
     * @param context   上下文
     * @param url       请求地址
     * @param tag       标注用来取消时使用
     * @param listenner 回调
     * @param isCache   是否走缓存
     */
    public void get(Context context, String url, String tag, ItemResultListenner listenner, boolean isCache) {
        sendGet(context, url, tag, listenner, isCache);
    }

    /**
     * Get请求
     *
     * @param url       请求的URL
     * @param tag       标注用来取消时使用
     * @param listenner 回调监听
     * @param isCache   是否走缓存
     */
    private void sendGet(final Context context, String url, String tag,
                         final ItemResultListenner listenner, boolean isCache) {
        Log.log(url);
        VolleyUtils.getInstance(context).volleyGet(url, tag, new ResponseResultListenner() {
            @Override
            public void success(Object resultObj) {
                Log.writeSystemLog("http request success,and return：" + resultObj.toString());
                listenner.success(resultObj.toString());
            }

            @Override
            public void faild(Object failCode, Object failMsg) {
                Log.writeSystemLog("http request faild：" + failMsg.toString());
                listenner.httpfaild();
            }
        }, isCache);
    }
}
