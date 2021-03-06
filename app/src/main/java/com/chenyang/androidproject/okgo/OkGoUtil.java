package com.chenyang.androidproject.okgo;

import android.util.Log;

import com.chenyang.androidproject.okgo.callbck.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.Map;

/**
 * Created by ccb on 2017/10/12.
 * 网络框架二次封装
 */

public class OkGoUtil {
    /**
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public static <T> void getRequets(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        Log.d("OkGoUtil", "method get");
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

    public static <T> void postRequest(String url, Object tag, Map<String, String> map,JsonCallback<T> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        Log.d("OkGoUtil", "method post");
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

}
