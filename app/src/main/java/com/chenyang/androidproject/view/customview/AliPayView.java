package com.chenyang.androidproject.view.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by qijian on 16/12/25.
 */
public class AliPayView extends View {
    private Path mCirclePath, mDstPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private Float mCurAnimValue;
    private int mCentX = 100;
    private int mCentY = 100;
    private int mRadius = 50;

public AliPayView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setLayerType(LAYER_TYPE_SOFTWARE, null);

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(4);
    mPaint.setColor(Color.BLACK);

    mDstPath = new Path();
    mCirclePath = new Path();

    mCirclePath.addCircle(mCentX, mCentY, mRadius, Path.Direction.CW);

    mCirclePath.moveTo(mCentX - mRadius / 2, mCentY);
    mCirclePath.lineTo(mCentX, mCentY + mRadius / 2);
    mCirclePath.lineTo(mCentX + mRadius / 2, mCentY - mRadius / 3);

    mPathMeasure = new PathMeasure(mCirclePath, false);

    ValueAnimator animator = ValueAnimator.ofFloat(0, 2);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            mCurAnimValue = (Float) animation.getAnimatedValue();
            invalidate();
        }
    });
    animator.setDuration(4000);
    animator.start();
}

boolean mNext = false;
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawColor(Color.WHITE);
    if (mCurAnimValue < 1) {
        float stop = mPathMeasure.getLength() * mCurAnimValue;
        mPathMeasure.getSegment(0, stop, mDstPath, true);
    } else {
        if (!mNext) {
            mNext = true;
            mPathMeasure.getSegment(0, mPathMeasure.getLength(), mDstPath, true);
            mPathMeasure.nextContour();
        }
        float stop = mPathMeasure.getLength() * (mCurAnimValue - 1);
        mPathMeasure.getSegment(0, stop, mDstPath, true);
    }
    canvas.drawPath(mDstPath, mPaint);
}
}
