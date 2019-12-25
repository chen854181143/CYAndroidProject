package com.chenyang.androidproject.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.customview.FallingBallImageView;
import com.chenyang.androidproject.view.evaluator.FallingBallEvaluator;

import butterknife.BindView;

public class CustomViewStudentSevenActivity extends MyActivity {

    @BindView(R.id.startAnim)
    AppCompatButton startAnim;
    @BindView(R.id.cancelAnim)
    AppCompatButton cancelAnim;
    @BindView(R.id.button1)
    AppCompatButton button1;
    @BindView(R.id.button2)
    AppCompatButton button2;
    private AnimatorSet animatorSet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_student_seven;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        startAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doListenerAnimation();
            }
        });
        cancelAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.cancel();
            }
        });
    }

    private void doListenerAnimation() {
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(button1, "translationY", 0, 400, 0);
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(button2, "translationY", 0, 400, 0);
//        translationY2.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.play(translationY2).before(translationY1);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.setDuration(2000);
        animatorSet.start();
    }


    @Override
    protected void initData() {

    }


}
