package com.chenyang.androidproject.activity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.customview.FallingBallImageView;
import com.chenyang.androidproject.view.evaluator.CharEvaluator;
import com.chenyang.androidproject.view.evaluator.FallingBallEvaluator;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;

public class CustomViewStudentSixActivity extends MyActivity {

    @BindView(R.id.btn)
    AppCompatButton btn;
    @BindView(R.id.image)
    AppCompatImageView image;
    @BindView(R.id.btn1)
    AppCompatButton btn1;
    @BindView(R.id.image1)
    AppCompatImageView image1;
    @BindView(R.id.btn2)
    AppCompatButton btn2;
    @BindView(R.id.image2)
    AppCompatImageView image2;

    @BindView(R.id.btn3)
    AppCompatButton btn3;
    @BindView(R.id.image3)
    AppCompatImageView image3;
    @BindView(R.id.btn4)
    AppCompatButton btn4;
    @BindView(R.id.image4)
    AppCompatImageView image4;

    @BindView(R.id.btn5)
    AppCompatButton btn5;
    @BindView(R.id.image5)
    AppCompatImageView image5;
    @BindView(R.id.btn6)
    AppCompatButton btn6;
    @BindView(R.id.image6)
    AppCompatImageView image6;
    @BindView(R.id.btn7)
    AppCompatButton btn7;
    @BindView(R.id.image7)
    FallingBallImageView image7;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_student_six;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image, "rotationX", 0f, 270f, 0f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image1, "rotationY", 0f, 270f, 0f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image2, "rotation", 0f, 270f, 0f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image3, "translationX", 0f, 270f, 0f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image4, "translationY", 0f, 270f, 0f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image5, "scaleX", 0f, 3f, 1f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(image6, "scaleY", 0f, 3f, 1f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofObject(image7, "fallingPos", new FallingBallEvaluator(), new Point(0, 0), new Point(500, 500));
                objectAnimator.setDuration(2000);
                objectAnimator.start();
            }
        });
    }


    @Override
    protected void initData() {

    }


}
