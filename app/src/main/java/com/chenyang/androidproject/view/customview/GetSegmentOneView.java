package com.chenyang.androidproject.view.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

public class GetSegmentOneView extends View {

    private final ValueAnimator valueAnimator;
    private final PathMeasure pathMeasure;
    private Float mCurAnimValue;
    private final Path mDstPath;
    private final Paint paint;

    public GetSegmentOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);

        mDstPath = new Path();
        Path mCircle = new Path();
        mCircle.addCircle(100, 100, 50, Path.Direction.CW);
        pathMeasure = new PathMeasure(mCircle, true);
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurAnimValue = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float length = pathMeasure.getLength();
        float stop = length * mCurAnimValue;
        float start = (float) (stop - ((0.5 - Math.abs(mCurAnimValue - 0.5)) * length));
        mDstPath.reset();
        canvas.drawColor(Color.WHITE);
        pathMeasure.getSegment(start, stop, mDstPath, true);
        canvas.drawPath(mDstPath, paint);
    }
}
