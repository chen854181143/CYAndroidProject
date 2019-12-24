package com.chenyang.androidproject.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

public class BasisOneView extends View {
    public BasisOneView(Context context) {
        super(context);
    }

    public BasisOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasisOneView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*Paint paint = new Paint();
        paint.setColor(Color.RED); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //填充样式改为描边
        paint.setStrokeWidth(5); //设置画笔宽度
        Path path = new Path();
        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100); //第一条直线的终点，也是第二条直线的起始点
        path.lineTo(300, 100); //画第二条直线
        path.close(); //闭环
        canvas.drawPath(path, paint);*/

        /*Path path = new Path();
        path.moveTo(10,10);
        RectF rectF = new RectF(100,10,200,100);
        path.arcTo(rectF,0,90,true);
        canvas.drawPath(path,paint);*/

        /*Path path = new Path();
        path.moveTo(10,10);
        path.lineTo(100,50);
        RectF rectF = new RectF(100,100,150,150);
        path.addArc(rectF,0,90);
        canvas.drawPath(path, paint);*/

        //先创建两条大小相同的路径
        //第一条路径逆向生成
        /*Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

        //第二条路径顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        //先画出这两条路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);*/

        //先创建两条大小相同的路径
        //第一条路径逆向生成
        /*Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);
        //第二条路径顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);
        //先画出这两条路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);
        //依据路径布局文字
        String text = "苦心人天不负,有志者事竟成";
        paint.setColor(Color.GREEN);
        paint.setTextSize(35);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针方向生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针方向生成*/

        /*Path path = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        path.addRoundRect(rect1, 10, 15 , Path.Direction.CCW);

        RectF rect2 = new RectF(290, 50, 480, 200);
        float radii[] ={10,15,20,25,30,35,40,45};
        path.addRoundRect(rect2, radii, Path.Direction.CCW);

        canvas.drawPath(path, paint);*/

       /* Path path = new Path();
        path.addCircle(100, 100, 50, Path.Direction.CCW);
        canvas.drawPath(path, paint);*/

        /*Path path = new Path();
        RectF rect = new RectF(10, 10, 200, 100);
        path.addOval(rect, Path.Direction.CCW);
        canvas.drawPath(path, paint);*/

       /* Path path = new Path();
        RectF rect = new RectF(10, 10, 100, 50);
        path.addArc(rect, 0, 100);
        canvas.drawPath(path, paint);*/

        /*Paint paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint1.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.addRect(100, 100, 300, 300, Path.Direction.CW);
        path.addCircle(300, 300, 100, Path.Direction.CW);
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(path,paint1);*/

        /*Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_WINDING);
        path.reset();
        path.addCircle(100, 100, 50, Path.Direction.CW);
        canvas.drawPath(path, paint1);*/

        /*Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_WINDING);
        path.rewind();
        path.addCircle(100, 100, 50, Path.Direction.CW);
        canvas.drawPath(path, paint1);*/

       /* Paint paint=new Paint();
        paint.setColor(Color.RED); //设置画笔颜色
        paint.setStrokeWidth (5); //设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能。如果使用，则会使绘图速度变慢
        paint.setTextSize(100); //设置文字大小
        //绘图样式，设置为填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("床前明月光", 10,100, paint);*/

        /*Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        canvas.drawText("床前明月光", 10,100, paint);
        paint.setFakeBoldText(true); //设置粗体文字
        paint.setUnderlineText(true); //设置下画线
        paint.setStrikeThruText(true); //设置带有删除线效果
        canvas.drawText("床前明月光", 10,250, paint);*/

        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        //正常样式
        canvas.drawText("床前明月光", 10, 100, paint);
        //向右倾斜
        paint.setTextSkewX(-0.25f);
        canvas.drawText("床前明月光", 10, 200, paint);
        //向左倾斜
        paint.setTextSkewX(0.25f);
        canvas.drawText("床前明月光", 10, 300, paint);*/

        /*Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        //正常样式
        canvas.drawText("床前明月光", 10,100, paint);
        //拉伸 2 倍
        paint.setTextScaleX(2);
        canvas.drawText("床前明月光", 10,200, paint);*/

       /* Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        float[] pos = new float[]{80, 100,
                80, 200,
                80, 300,
                80, 400};
        canvas.drawPosText("床前明月", pos, paint);*/

        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setTextSize(45);
        paint.setStyle(Paint.Style.STROKE);
        //先创建两条相同的圆形路径，并画出两条路径原形
        Path circlePath = new Path();
        circlePath.addCircle(220, 300, 150, Path.Direction.CCW);//逆向绘制
        canvas.drawPath(circlePath, paint); //绘制出路径原形
        Path circlePath2 = new Path();
        circlePath2.addCircle(600, 300, 150, Path.Direction.CCW);
        canvas.drawPath(circlePath2, paint); //绘制出路径原形
        //绘制原始文字与偏移文字
        String string = "床前明月光,疑是地上霜";
        paint.setColor(Color.GREEN);
        //将 hOffset、vOffset 参数值全部设为 0，看原始状态是怎样的
        canvas.drawTextOnPath(string, circlePath, 0, 0, paint);
        //第二条路径，改变 hOffset、vOffset 参数值
        canvas.drawTextOnPath(string, circlePath2, 80, 30, paint);*/

       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        Region region = new Region(new Rect(50,50,200,100));
        drawRegion(canvas,region,paint);*/

        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        //构造一条椭圆路径
        Path ovalPath = new Path();
        RectF rect = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rect, Path.Direction.CCW);
        //在 setPath()函数中传入一个比椭圆区域小的矩形区域，让其取交集
        Region rgn = new Region();
        rgn.setPath(ovalPath, new Region(50, 50, 200, 200));
        //画出路径
        drawRegion(canvas, rgn, paint);*/

       /* Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        //构造一条椭圆路径
        Path ovalPath = new Path();
        RectF rect = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rect, Path.Direction.CCW);
        //构造椭圆区域
        Region rgn = new Region();
        rgn.setPath(ovalPath, new Region(50, 50, 200, 500));
        drawRegion(canvas,rgn,paint);*/

        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        Region region = new Region(10,10,200,100);
        region.union(new Rect(10,10,50,300));
        drawRegion(canvas,region,paint);*/

        //构造两个矩形
        /*Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);
        //构造一个画笔，画出矩形的轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(rect1, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect2, paint);
        //构造两个区域
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);
        //取两个区域的交集
        region.op(region2, Region.Op.REPLACE);
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);*/

       /* Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

         canvas.translate(100, 100);
        Rect rect1 = new Rect(0,0,400,220);
        canvas.drawRect(rect1, paint);*/

        //构造两个画笔，一个红色，一个绿色
       /* Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
        Paint paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3);

        //构造一个矩形
        Rect rect1 = new Rect(0,0,400,220);

        //在平移画布前用绿色画笔画下边框
        canvas.drawRect(rect1, paint_green);

        //在平移画布后，再用红色画笔重新画下边框
        canvas.translate(100, 100);
        canvas.drawRect(rect1, paint_red);*/

        /*Paint paint_green = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(300, 10, 500, 100);
        canvas.drawRect(rect1, paint_red); //画出原轮廓

        canvas.rotate(30); //顺时针旋转画布
        canvas.drawRect(rect1, paint_green); //画出旋转后的矩形*/

        /*Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 5);
        Paint paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 5);
        Rect rect1 = new Rect(10,10,200,100);
        canvas.drawRect(rect1, paint_green);
        canvas.scale(0.5f, 1);
        canvas.drawRect(rect1, paint_red);*/

        /*canvas.drawColor(Color.RED);
        canvas.clipRect(new Rect(100, 100, 200, 200));
        canvas.drawColor(Color.GREEN);*/

        /*canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        int c1 = canvas.save();
        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存的画布大小为 Rect(100, 100, 800, 800)
        int c2 = canvas.save();
        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存的画布大小为 Rect(200, 200, 700, 700)
        int c3 = canvas.save();
        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存的画布大小为 Rect(300, 300, 600, 600)
        int c4 = canvas.save();
        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);
        //连续三次出栈，将最后一 画布状态作为当前画布 次出栈的 ，并填充为黄色
        canvas.restoreToCount(c2);
        canvas.drawColor(Color.YELLOW);*/
    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();
        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

}
