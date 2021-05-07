package com.chenyang.androidproject.utils.capture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.chenyang.androidproject.R;


public class GameView extends View {
    //声明Paint对象
    private Paint mPaint = null;
    private int StrokeWidth = 5;
    private Rect rect = new Rect(0, 0, 0, 0);//手动绘制矩形

    public GameView(Context context,Rect rect) {
        super(context);
        //构建对象
        mPaint = new Paint();
        this.rect = rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置无锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setColor(getResources().getColor(R.color.blue));
        mPaint.setAlpha(100);
        canvas.drawRect(rect, mPaint);
    }
}