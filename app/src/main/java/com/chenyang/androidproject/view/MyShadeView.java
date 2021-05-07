package com.chenyang.androidproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyShadeView extends FrameLayout {
    private Paint mPaint;
    private int mX, mY, mRadius;

    public MyShadeView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyShadeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyShadeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#FFFFFF"));
        mX = 150;
        mY = 100;
        mRadius = 500;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(porterDuffXfermode);
        mPaint.setAntiAlias(true);
    }

    public void setCircleLocation(int x, int y, int radius) {
        this.mX = x;
        this.mY = y;
        this.mRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }
}

