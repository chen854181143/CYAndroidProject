package com.chenyang.androidproject.activity;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;

import butterknife.BindView;

public class CameraStretchDemoActivity extends MyActivity {

    @BindView(R.id.image)
    AppCompatImageView image;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_stretch_demo;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(6000);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        image.startAnimation(scaleAnimation);
    }

    @Override
    protected void initData() {

    }


}
