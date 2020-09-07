package com.chenyang.androidproject.activity;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;

import butterknife.BindView;

public class CustomViewStudentFourActivity extends MyActivity {

    @BindView(R.id.btn)
    AppCompatButton btn;
    @BindView(R.id.tv)
    AppCompatTextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_student_four;
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
//                Animation animation=AnimationUtils.loadAnimation(CustomViewStudentFourActivity.this,R.anim.scaleanim);
//                Animation animation=AnimationUtils.loadAnimation(CustomViewStudentFourActivity.this,R.anim.alphaanim);
//                Animation animation=AnimationUtils.loadAnimation(CustomViewStudentFourActivity.this,R.anim.rotateanim);
                Animation animation = AnimationUtils.loadAnimation(CustomViewStudentFourActivity.this, R.anim.translateanim);
//                Animation animation = AnimationUtils.loadAnimation(CustomViewStudentFourActivity.this, R.anim.setanim);
//                animation.setInterpolator(new AccelerateDecelerateInterpolator());
//                animation.setInterpolator(new AccelerateInterpolator());
//                animation.setInterpolator(new DecelerateInterpolator());
//                animation.setInterpolator(new LinearInterpolator());
//                animation.setInterpolator(new BounceInterpolator());
//                animation.setInterpolator(new AnticipateInterpolator(0.5f));
//                animation.setInterpolator(new OvershootInterpolator(4));
//                animation.setInterpolator(new AnticipateOvershootInterpolator());
                animation.setInterpolator(new CycleInterpolator(2));
                tv.startAnimation(animation);

                /*ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(700);
                tv.startAnimation(scaleAnimation);*/
                /*AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
                alphaAnimation.setDuration(3000);
                alphaAnimation.setFillAfter(true);
                alphaAnimation.setInterpolator(new LinearInterpolator());
                tv.startAnimation(alphaAnimation);*/
                /*RotateAnimation rotateAnimation = new RotateAnimation(0, -650, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(3000);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tv.startAnimation(scaleAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tv.startAnimation(rotateAnimation);*/

                /*TranslateAnimation translateAnimation=new TranslateAnimation(Animation.ABSOLUTE,0,Animation.ABSOLUTE,-80,Animation.ABSOLUTE,0,Animation.ABSOLUTE,-80);
                translateAnimation.setDuration(3000);
                translateAnimation.setFillBefore(true);
                tv.startAnimation(translateAnimation);*/

                /*AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.1f);
                ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,1.4f,0.0f,1.4f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                RotateAnimation rotateAnimation = new RotateAnimation(0, -650, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                AnimationSet animationSet=new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(rotateAnimation);
                animationSet.setDuration(3000);
                animationSet.setFillAfter(true);
                tv.startAnimation(animationSet);*/
            }
        });
    }

    @Override
    protected void initData() {

    }


}
