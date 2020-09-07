package com.chenyang.androidproject.activity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.evaluator.CharEvaluator;
import com.chenyang.androidproject.view.evaluator.FallingBallEvaluator;
import com.chenyang.androidproject.view.evaluator.MyEvalutor;
import com.chenyang.androidproject.view.interpolator.MyInterpolator;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;

public class CustomViewStudentFiveActivity extends MyActivity {

    @BindView(R.id.btn)
    AppCompatButton btn;
    @BindView(R.id.tv)
    AppCompatTextView tv;
    @BindView(R.id.btn1)
    AppCompatButton btn1;
    @BindView(R.id.tv1)
    AppCompatTextView tv1;
    @BindView(R.id.btn2)
    AppCompatButton btn2;
    @BindView(R.id.image)
    AppCompatImageView image;

    private static final String TAG = "CustomViewStudentFiveActivity";
    private ValueAnimator animator;
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimator1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_student_five;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        /*ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 400);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue = (Integer) valueAnimator.getAnimatedValue();
                LogUtils.info(TAG, "curValue:" + curValue);
            }
        });
        valueAnimator.start();*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAnimation();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAnimation1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAnimation2();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("点击我了");
            }
        });
    }

    private void doAnimation2() {
        animator = ValueAnimator.ofObject(new FallingBallEvaluator(), new Point(0, 0), new Point(500, 500));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                image.layout(point.x, point.y, point.x + image.getWidth(), point.y + image.getHeight());
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

    private void doAnimation1() {
        valueAnimator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                char text = (Character) valueAnimator.getAnimatedValue();
                tv1.setText(String.valueOf(text));
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    private void doAnimation() {
        /*ValueAnimator animator = ValueAnimator.ofInt(0, 400);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue = (Integer) valueAnimator.getAnimatedValue();
                tv.layout(curValue, curValue, curValue + tv.getWidth(), curValue + tv.getHeight());
            }
        });*/
        /*animator.start();*/
        /*ValueAnimator animator = ValueAnimator.ofFloat(0f, 400f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float curValueFloat = (Float) valueAnimator.getAnimatedValue();
                int curValue=curValueFloat.intValue();
                tv.layout(curValue, curValue, curValue + tv.getWidth(), curValue + tv.getHeight());
            }
        });*/
      /*  animator.addListener(new Animator.AnimatorListener() {
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
        });*/
//        animator.setInterpolator(new MyInterpolator());
//        animator.setEvaluator(new MyEvalutor());
//        animator.start();

        valueAnimator1 = ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        valueAnimator1.setEvaluator(new ArgbEvaluator());
        valueAnimator1.setDuration(3000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue = (Integer) valueAnimator.getAnimatedValue();
                tv.setBackgroundColor(curValue);
            }
        });
        valueAnimator1.start();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator.cancel();
        }
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (valueAnimator1 != null) {
            valueAnimator1.cancel();
        }
    }
}
