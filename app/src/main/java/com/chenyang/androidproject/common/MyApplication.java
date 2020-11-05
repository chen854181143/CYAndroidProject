package com.chenyang.androidproject.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.chenyang.androidproject.fresco.FrescoInitUtil;
import com.chenyang.androidproject.utils.FontModel;
import com.chenyang.androidproject.utils.MyFileNameGenerator;
import com.danikula.videocache.HttpProxyCacheServer;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySkyConfig;
import com.lzx.starrysky.common.IMediaConnection;
import com.lzx.starrysky.notification.INotification;
import com.lzx.starrysky.notification.NotificationConfig;
import com.lzx.starrysky.utils.StarrySkyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的Application基类
 */
public class MyApplication extends UIApplication {

    private static Context mContext;

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .fileNameGenerator(new MyFileNameGenerator())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 为了优化启动速度，请将一些没必须一定要在 Application 初始化的第三方框架移步至 LauncherActivity 中的 initData 方法中
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        initOkGo();
        initIconify();
        initFrescoConfig();
        initStarrySky();
        
    }

    private void initStarrySky() {
//        NotificationConfig notificationConfig = new NotificationConfig();
//        notificationConfig.setTargetClass("com.lzx.musiclib.example.PlayDetailActivity");
//        notificationConfig.setFavoriteIntent(getPendingIntent(INotification.ACTION_FAVORITE));

        StarrySkyConfig config = new StarrySkyConfig().newBuilder()
//                .addInterceptor(PermissionInterceptor(this))
//                .addInterceptor(RequestSongInfoInterceptor())
//            .addInterceptor(PlayVoiceBeforeRealPlay(this))
//                .isOpenNotification(false)
//                .setNotificationConfig(notificationConfig)
//            .setNotificationFactory(StarrySkyNotificationManager.CUSTOM_NOTIFICATION_FACTORY)
//            .isOpenCache(true)
//            .setCacheDestFileDir(
//                Environment.getExternalStorageDirectory().absolutePath.toString() +
//                    "/111StarrySkyCache/")
//            .setNotificationFactory(MyNotificationFactory())
//            .setPlayback(MediaPlayback(this, null))
//            .setPlayerControl(MyPlayerControl())
//            .setMediaQueueProvider(MyMediaQueueProvider())
//            .setMediaQueue(MyMediaQueue())
//                .setImageLoader(GlideLoader())
//            .setMediaConnection(MyMediaConnection())
//            .setCache(MyCache(this))
                .build();
        StarrySky.init(this, config, new IMediaConnection.OnConnectListener() {
            @Override
            public void onConnected() {

            }
        });
    }

//    private PendingIntent getPendingIntent(String action) {
//        Intent intent = new Intent(action);
//        intent.setClass(this, NotificationReceiver.class);
//        return PendingIntent.getBroadcast(this, 0, intent, 0);
//    }

    /**
     * 初始化Fresco
     */
    private void initFrescoConfig() {
        FrescoInitUtil.initFrescoConfig();
    }

    /**
     * 初始化字体图标库
     */
    private void initIconify() {
        Iconify
                .with(new FontAwesomeModule())
//                .with(new IoniconsModule())
                .with(new FontModel());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);
    }

    /**
     * 返回Context
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * okgo的初始化操作
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
//        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        builder.readTimeout(10000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)              //不使用缓存
//                .setCacheTime(1000*60*60*24)                  //全局统一缓存时间，缓存为24小时
                .setRetryCount(0);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

}