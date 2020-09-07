package com.chenyang.androidproject.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chenyang.androidproject.view.gloading.Gloading;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中 Fragment 懒加载基类
 */
public abstract class MyLazyFragment extends UILazyFragment
        implements OnTitleBarListener {

    private Unbinder mButterKnife; // View注解
    private String currentTag;

    public void setCurrentTag(String currentTag) {
        this.currentTag = currentTag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        // 初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
//        UmengHelper.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 友盟统计
//        UmengHelper.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
        if(!TextUtils.isEmpty(currentTag)){
            OkGo.getInstance().cancelTag(currentTag);
        }
    }

    @Nullable
    public TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(int id) {
        ToastUtils.show(id);
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }

    // 标题栏左边的View被点击了
    @Override
    public void onLeftClick(View v) {}

    // 标题栏中间的View被点击了
    @Override
    public void onTitleClick(View v) {}

    // 标题栏右边的View被点击了
    @Override
    public void onRightClick(View v) {}


}