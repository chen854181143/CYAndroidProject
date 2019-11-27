package com.chenyang.androidproject.activity;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import com.airbnb.lottie.LottieAnimationView;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;

import butterknife.BindView;

/**
 * Lottie学习
 */
public class LottieStudentActivity extends MyActivity {
    @BindView(R.id.animation_view1)
    LottieAnimationView animationView1;
    @BindView(R.id.animation_view2)
    LottieAnimationView animationView2;
    @BindView(R.id.animation_view3)
    LottieAnimationView animationView3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottie_student;
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
        animationView3.setAnimation("x_pop.json");
        animationView3.loop(false);
        animationView3.playAnimation();
        animationView3.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
