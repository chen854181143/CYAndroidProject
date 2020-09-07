package com.chenyang.androidproject.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;

import butterknife.BindView;

public class ScannerDemoActivity extends MyActivity {

    @BindView(R.id.circle1)
    AppCompatImageView circle1;
    @BindView(R.id.circle2)
    AppCompatImageView circle2;
    @BindView(R.id.circle3)
    AppCompatImageView circle3;
    @BindView(R.id.circle4)
    AppCompatImageView circle4;
    @BindView(R.id.start_can)
    AppCompatTextView start_can;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scanner_demo;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        Animation animation1=AnimationUtils.loadAnimation(ScannerDemoActivity.this,R.anim.scale_alpha_anim);
        Animation animation2=AnimationUtils.loadAnimation(ScannerDemoActivity.this,R.anim.scale_alpha_anim);
        Animation animation3=AnimationUtils.loadAnimation(ScannerDemoActivity.this,R.anim.scale_alpha_anim);
        Animation animation4=AnimationUtils.loadAnimation(ScannerDemoActivity.this,R.anim.scale_alpha_anim);
        start_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle1.startAnimation(animation1);
                animation2.setStartOffset(600);
                circle2.startAnimation(animation2);
                animation3.setStartOffset(1200);
                circle3.startAnimation(animation3);
                animation4.setStartOffset(1800);
                circle4.startAnimation(animation4);
            }
        });
    }

    @Override
    protected void initData() {

    }


}
