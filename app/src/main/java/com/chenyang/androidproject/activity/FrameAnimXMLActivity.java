package com.chenyang.androidproject.activity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;

import butterknife.BindView;

public class FrameAnimXMLActivity extends MyActivity {

    @BindView(R.id.frame_image)
    AppCompatImageView image;

    @Override
    protected int getLayoutId() {
        return R.layout.frame_anim_xml_activity;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        final AnimationDrawable anim = (AnimationDrawable) image.getBackground();
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                anim.start();
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                anim.stop();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
