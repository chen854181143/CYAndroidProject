package com.chenyang.androidproject.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BasisView extends View {
    public BasisView(Context context) {
        super(context);
    }

    public BasisView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasisView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*canvas.drawRGB(255,0,255);
        canvas.drawColor(0xFFFF00FF);
        canvas.drawARGB(0xFF,0xFF,0,0xFF);*/

        /*//设置画笔的基本属性
        Paint paint = new Paint();
        paint.setColor(Color.RED); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //设置填充样式
        paint.setStrokeWidth(50); //设置画笔宽度
        paint.setAntiAlias(true); //打开抗锯齿功能
        //画圆
        canvas.drawCircle(190, 200, 150, paint);*/


        /*Paint paint=new Paint();
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(50);
        canvas.drawCircle(190, 200, 150, paint);
        paint.setColor(0x7EFFFF00);

        canvas.drawCircle(190, 200, 100, paint);*/

        /*Paint paint=new Paint();
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(50);
        canvas.drawCircle(190, 200, 150, paint);*/


       /* Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        canvas.drawLine(100, 100, 200, 200, paint);*/

       /* Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        float []pts={10,10,100,100,200,200,400,400};
        canvas.drawLines(pts, paint);*/

        /*Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        float []pts={10,10,100,100,200,200,300,300,1000,1000};
        canvas.drawLines(pts,2,4,paint);*/

        /*Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);
        canvas.drawPoint(100, 100, paint);*/

       /* Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(25);
        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
        canvas.drawPoints(pts, 2, 4, paint);*/

        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        //直接构造
        canvas.drawRect(10, 10, 100, 100, paint);
        //使用 RectF 构造
        paint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(210f, 10f, 300f, 100f);
        canvas.drawRect(rect, paint);*/


        /*Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(15);

        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRoundRect(rect, 20, 10, paint);*/

       /* Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.GREEN);//更改画笔颜色
        canvas.drawOval(rect, paint);//根据同一个矩形画椭圆*/

       /* Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        //带两边
        RectF rect1 = new RectF(10, 10, 100, 100);
        canvas.drawArc(rect1, 0, 90, true, paint);
        //不带两边
        RectF rect2 = new RectF(110, 10, 200, 100);
        canvas.drawArc(rect2, 0, 90, false, paint);*/

        /*Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Rect rect_1 = new Rect(10, 10, 200, 200);
        Rect rect_2 = new Rect(190, 10, 250, 200);
        Rect rect_3 = new Rect(10, 210, 200, 300);
        //分别画出三个矩形
        paint.setColor(Color.RED);
        canvas.drawRect(rect_1, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect_2, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rect_3, paint);
        //判断是否相交
        Boolean interset1_2 = Rect.intersects(rect_1, rect_2);
        Boolean interset1_3 = Rect.intersects(rect_1, rect_3);
        Log.d("qijian", "rect_1&rect_2:" + interset1_2 + " rect_1&rect_3:" + interset1_3);*/

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Rect rect_1 = new Rect(10,10,20,20);
        Rect rect_2 = new Rect(100,100,110,110);
        //分别画出源矩形 rect_1、rect_2
        paint.setColor(Color.RED);
        canvas.drawRect(rect_1,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect_2,paint);
        //画出合并之后的结果 rect_1
        paint.setColor(Color.YELLOW);
        rect_1.union(rect_2);
        canvas.drawRect(rect_1,paint);
    }
}
