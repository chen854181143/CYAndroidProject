package com.chenyang.androidproject.view.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.chenyang.androidproject.R;

public class ClipRgnView extends View {
    private Bitmap mBitmap;
    private int clipWidth = 0;
    private int width;
    private int heigth;
    private static final int CLIP_HEIGHT = 30;
    private Path mPath;

    public ClipRgnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.
                meijing);
        width = mBitmap.getWidth();
        heigth = mBitmap.getHeight();
//        mRgn = new Region();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        int i = 0;
        while (i * CLIP_HEIGHT <= heigth) {//计算 clip 的区域
            if (i % 2 == 0) {
                //替换了Region.union方法
                mPath.addRect(new RectF(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT), Path.Direction.CCW);
            } else {
                mPath.addRect(new RectF(width, i * CLIP_HEIGHT, width - clipWidth, (i + 1) * CLIP_HEIGHT), Path.Direction.CCW);
            }
            i++;
        }
        canvas.clipPath(mPath);
        canvas.drawBitmap(mBitmap, 0, 0, new Paint());
        if (clipWidth > width) {
            return;
        }
        clipWidth += 5;
        invalidate();
    }

}
