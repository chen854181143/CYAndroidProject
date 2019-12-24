package com.chenyang.androidproject.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chenyang.androidproject.helper.ActivityStackManager;
import com.chenyang.androidproject.view.gloading.Gloading;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;


import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends UIActivity
        implements OnTitleBarListener {
    protected Gloading.Holder mHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().onActivityCreated(this);
    }

    private Unbinder mButterKnife;//View注解

    @Override
    protected void initLayout() {
        super.initLayout();
        // 初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }
        mButterKnife = ButterKnife.bind(this);
        initOrientation();
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        // 当前 Activity 不能是透明的并且没有指定屏幕方向，默认设置为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    @Nullable
    public TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    /**
     * {@link OnTitleBarListener}
     */

    // 标题栏左边的View被点击了
    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    // 标题栏中间的View被点击了
    @Override
    public void onTitleClick(View v) {}

    // 标题栏右边的View被点击了
    @Override
    public void onRightClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计
//        UmengHelper.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
//        UmengHelper.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) mButterKnife.unbind();
        ActivityStackManager.getInstance().onActivityDestroyed(this);
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



    /**
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     *
     */
    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    /**
     * 正在加载
     */
    public void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    /**
     * 加载成功
     */
    public void showLoadSuccess() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();
    }

    /**
     * 加载失败
     */
    public void showLoadFailed() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }

    /**
     * 数据为空
     */
    public void showEmpty() {
        initLoadingStatusViewIfNeed();
        mHolder.showEmpty();
    }

}