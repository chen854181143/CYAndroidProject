package com.chenyang.androidproject.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.MyTextView;
import com.chenyang.androidproject.view.evaluator.CharEvaluator;

import butterknife.BindView;

public class CustomViewStudentEightActivity extends MyActivity {

    @BindView(R.id.btn)
    AppCompatButton btn;
    @BindView(R.id.tv)
    MyTextView tv;
    @BindView(R.id.btn1)
    AppCompatButton btn1;
    @BindView(R.id.tv1)
    MyTextView tv1;
    @BindView(R.id.tv2)
    MyTextView tv2;
    @BindView(R.id.btn_add)
    AppCompatButton btn_add;
    @BindView(R.id.btn_remove)
    AppCompatButton btn_remove;
    @BindView(R.id.ll)
    LinearLayoutCompat ll;
    private int i;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_student_eight;
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
                /*PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
                PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("Alpha", 0.1f, 1f, 0.1f, 1f);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv, rotationHolder, alphaHolder);
                objectAnimator.setDuration(3000);
                objectAnimator.start();*/
                /*PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText", new CharEvaluator(), new Character('A'), new Character('Z'));
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv, charHolder);
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                objectAnimator.start();*/
                Keyframe keyframe0 = Keyframe.ofFloat(0f, 0);
                Keyframe keyframe1 = Keyframe.ofFloat(0.1f, -20);
                Keyframe keyframe2 = Keyframe.ofFloat(1f, 0);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation", keyframe0, keyframe1, keyframe2);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv, propertyValuesHolder);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.animate().scaleY(2);
                tv2.animate().scaleYBy(2).setListener(new Animator.AnimatorListener() {
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
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = count1(ll);
                i++;
                Button button = new Button(CustomViewStudentEightActivity.this);
                button.setText("button" + i);
                LinearLayoutCompat.LayoutParams linearLayoutCompat = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(linearLayoutCompat);
                ll.addView(button, 0);
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = count1(ll);
                if (i > 0) {
                    ll.removeViewAt(0);
                }
                i--;
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 递归统计一个View的子View数(包含自身)
     *
     * @param root
     * @return
     */
    public int count1(View root) {
        int viewCount = 0;

        if (null == root) {
            return 0;
        }

        if (root instanceof ViewGroup) {
            viewCount++;
            for (int i = 0; i < ((ViewGroup) root).getChildCount(); i++) {
                View view = ((ViewGroup) root).getChildAt(i);
                if (view instanceof ViewGroup) {
                    viewCount += count1(view);
                } else {
                    viewCount++;
                }
            }
        }

        return viewCount;
    }


}
