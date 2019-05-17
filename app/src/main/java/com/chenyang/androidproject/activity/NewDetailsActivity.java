package com.chenyang.androidproject.activity;

import android.app.Activity;
import android.os.Bundle;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.base.BaseActivity;
import com.chenyang.androidproject.common.MyActivity;

public class NewDetailsActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_details;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

}
