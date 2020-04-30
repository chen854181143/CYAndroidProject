package com.chenyang.androidproject.view.mp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyScaleView extends View {
    private Paint scalePaint;
    public MyScaleView(Context context) {
        this(context, null);
    }
    public MyScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {
        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(Color.RED);
        scalePaint.setStrokeWidth(2);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setDither(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
    }
    private void drawScale(Canvas canvas) {
        int scaleGroup = 5;     // 默认1个长刻度间隔4个短刻度，加起来一组5
        int mScaleCount = 101;  //刻度的总个数
        canvas.save();
        int startX = 30;
        int startY = 500;   //即在坐标轴（30,500）处开始画刻度线
        for (int i = 0; i < mScaleCount; i++) {
            canvas.save();  //记录画布状态
            canvas.translate(10 * i, 0);    //画布平移，即刻度线的间距
            if (i == 0 || i % scaleGroup == 0) {
                canvas.drawLine(startX, startY, startX, startY - 20, scalePaint);//画长刻度线
            } else {
                canvas.drawLine(startX, startY, startX, startY - 10, scalePaint);//画短刻度线
            }
            canvas.restore();//取出保存的状态
        }
        canvas.restore();
    }
}
