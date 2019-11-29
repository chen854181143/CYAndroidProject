package com.chenyang.androidproject.okHttp;

import com.chenyang.androidproject.retrofit.LogInterceptor;
import com.chenyang.androidproject.utils.Constans;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientUtils {
    public static OkHttpClient getInstance() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(Constans.DEFAULT_TIME,TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
        return client;
    }
}
