package com.chenyang.androidproject.view.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.chenyang.androidproject.R;

public class CustomCircleView extends View {
    private Bitmap mBmp;
    private Paint mPaint;
    private Path mPath;

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mPaint = new Paint();
        mPath = new Path();
        int width = mBmp.getWidth();
        int height = mBmp.getHeight();
        mPath.addCircle(width / 2, height / 2, width / 2, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipPath(mPath);
        canvas.drawBitmap(mBmp, 0, 0, mPaint);
        canvas.restore();
    }
}
