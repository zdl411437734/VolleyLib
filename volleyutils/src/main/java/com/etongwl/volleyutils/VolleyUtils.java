package com.etongwl.volleyutils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.etongwl.volleyutils.net.ResponseResultListenner;

import java.io.File;
import java.util.Map;

/**
 * 类描述：volley工具类
 * 创建人：sarahstone
 * 创建时间：2016/6/12 14:14
 * 修改备注：
 */
public class VolleyUtils {

    //单例实例
    private static VolleyUtils instance;
    //应用实例
    private static Context application;
    //请求队列
    private RequestQueue mRequestQueue;
    //用于图片请求
    private ImageLoader mImageLoader;

    /**
     * 构造使用单例模式
     */
    public static VolleyUtils getInstance(Context application) {
        VolleyUtils.application = application;
        return LazyHolder.INSTANCE;
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * 获取图片加载
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    /**
     * volley发送post请求
     *
     * @param url      请求的url
     * @param tag      请求的标注--取消请求使用
     * @param params   请求参数
     * @param listener 回调监听
     */
    public void volleyPost(String url, Object tag, final Map<String, String> params, final ResponseResultListenner listener, boolean isCache) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (listener != null) {
                    listener.success(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (listener != null && volleyError != null && volleyError.networkResponse != null) {
                    listener.faild(volleyError.networkResponse.statusCode, "");
                } else {
                    listener.faild(-1, "无网络连接");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        //设置超时的时间为10妙,默认为2.5妙
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(tag);
        request.setShouldCache(isCache);
        mRequestQueue.add(request);
    }

    /**
     * volley发送get请求
     *
     * @param url      请求的url
     * @param tag      请求的标注--取消请求使用
     * @param listener 回调监听
     */
    public void volleyGet(String url, Object tag, final ResponseResultListenner listener, boolean isCache) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (listener != null) {
                    listener.success(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (listener != null) {
                    listener.faild(volleyError.networkResponse.statusCode, volleyError.getCause().getMessage());
                }
            }
        });
        //设置超时的时间为10妙,默认为2.5妙
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(tag);
        request.setShouldCache(isCache);
        mRequestQueue.add(request);
    }

    /**
     * 加载图片
     *
     * @param imgUrl        请求的图片URL
     * @param imageView     图片控件
     * @param default_image 默认图片
     * @param failed_image  加载失败图片
     * @param width         最大宽度
     * @param height        最大高度
     * @param sType         ScaleType类型
     */
    public void loaderImage(String imgUrl, ImageView imageView, int default_image, int failed_image, int width, int height, ImageView.ScaleType sType) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                default_image, failed_image);
        mImageLoader.get(imgUrl, listener, width, height, sType);
    }

    /**
     * 加载图片
     *
     * @param imgUrl        请求的图片URL
     * @param imageView     图片控件
     * @param default_image 默认图片
     * @param failed_image  加载失败图片
     * @param width         最大宽度
     * @param height        最大高度
     */
    public void loaderImage(String imgUrl, ImageView imageView, int default_image, int failed_image, int width, int height) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                default_image, failed_image);
        mImageLoader.get(imgUrl, listener, width, height);
    }

    /**
     * 加载图片
     *
     * @param imgUrl        请求的图片URL
     * @param imageView     图片控件
     * @param default_image 默认图片
     * @param failed_image  加载失败图片
     */
    public void loaderImage(String imgUrl, ImageView imageView, int default_image, int failed_image) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                default_image, failed_image);
        mImageLoader.get(imgUrl, listener);
    }

    /**
     * 在生命周期的onStop()进行注销
     *
     * @param tag 注销的tag
     */
    public void requestCancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }


    /**
     * 构造函数
     */
    private VolleyUtils() {
        //初始化内存缓存目录
        File cacheDir = new File(VolleyUtils.application.getCacheDir(), "volley");
        /**
         *初始化RequestQueue,
         * 其实这里你可以使用Volley.newRequestQueue来创建一个RequestQueue,
         * 直接使用构造函数可以定制我们需要的RequestQueue,比如线程池的大小等等
         */
        mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), new BasicNetwork(new HurlStack()), 3);
        //初始化图片内存缓存
        MemoryCache mCache = new MemoryCache();
        //初始化ImageLoader
        mImageLoader = new ImageLoader(mRequestQueue, mCache);
        //mRequestQueue = Volley.newRequestQueue(getApplicationContext());//使用全局上下文
        //如果调用Volley.newRequestQueue,那么下面这句可以不用调用
        mRequestQueue.start();
    }

    /**
     * 图片内存加载
     */
    public class MemoryCache implements ImageLoader.ImageCache {
        private static final String TAG = "MemoryCache";
        private LruCache<String, Bitmap> mCache;

        public MemoryCache() {
            //这个取单个应用最大使用内存的1/8
            int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    //这个方法一定要重写，不然缓存没有效果
                    return value.getHeight() * value.getRowBytes();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String key) {
            return mCache.get(key);
        }

        @Override
        public void putBitmap(String key, Bitmap value) {
            mCache.put(key, value);
        }
    }


    /**
     * 实现了线程安全，又避免了同步带来的性能影响
     */
    private static class LazyHolder {
        private static final VolleyUtils INSTANCE = new VolleyUtils();
    }

}
