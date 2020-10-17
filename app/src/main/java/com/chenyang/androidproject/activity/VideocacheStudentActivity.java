package com.chenyang.androidproject.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.utils.Utils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.hjq.toast.ToastUtils;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

import static io.agora.rtc.Constants.AUDIO_RECORDING_QUALITY_HIGH;

/**
 * 饺子播放器边播边缓存实现
 */
public class VideocacheStudentActivity extends MyActivity {

    @BindView(R.id.jz_video)
    JzvdStd jzvdStd;

    private String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //返回code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_cache_student;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        requestPermission();
    }

    private void initVideo() {
        HttpProxyCacheServer proxy = MyApplication.getProxy(this);
        //1.我们会将原始url注册进去
        // proxy.registerCacheListener(, bean.getVideo_url());
        //2.我们播放视频的时候会调用以下代码生成proxyUrl
        String proxyUrl = proxy.getProxyUrl(url);
        if (proxy.isCached(url)) {
            Log.i("aaaa", "已缓存");
        } else {
            Log.i("aaaa", "未缓存");
        }
        Log.i("视频缓存地址:", proxyUrl);
        jzvdStd.setUp(proxyUrl,
                "测试", Jzvd.SCREEN_NORMAL);
        Glide.with(getActivity()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg").into(jzvdStd.thumbImageView);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        if (lacksPermission()) {//判断是否拥有权限
            //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {
            //拥有权限执行操作
            initVideo();
        }
    }


    //如果返回true表示缺少权限
    public boolean lacksPermission() {
        for (String permission : permissions) {
            //判断是否缺少权限，true=缺少权限
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {//响应Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    //拥有权限执行操作
                    initVideo();
                } else {
                    Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

}
