package com.chenyang.androidproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.chenyang.androidproject.R;

/**
 * 自定义的TextView
 */
public class CustomTextView extends AppCompatTextView {

    private Paint paint;
    private String textContent;
    private Context context;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextView);
        paint = new Paint();
        paint.setColor(typedArray.getColor(R.styleable.CustomTextView_ctv_textColor, context.getResources().getColor(R.color.black)));
        paint.setTextSize(typedArray.getDimension(R.styleable.CustomTextView_ctv_textSize, 18));
        paint.setAntiAlias(true);
        textContent = typedArray.getString(R.styleable.CustomTextView_ctv_text);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * View 的 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1. 获取 自定义 View 的宽度，高度 的模式
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.AT_MOST == heigthMode) {
            Rect bounds = new Rect();
            paint.getTextBounds(textContent, 0, textContent.length(), bounds);
            height = bounds.height() + getPaddingBottom() + getPaddingTop();
        }

        if (MeasureSpec.AT_MOST == widthMode) {
            Rect bounds = new Rect();
            paint.getTextBounds(textContent, 0, textContent.length(), bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }

        setMeasuredDimension(width, height + 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        int x = getPaddingLeft();
        // x: 开始的位置  y：基线
        canvas.drawText(textContent, x, baseLine, paint);
    }

    /**
     * @param isBold           //设置粗体文字
     * @param isHaveUnderline  //设置下画线
     * @param isHaveStrikeThru //设置带有删除线效果
     */
    public void setStyle(boolean isBold, boolean isHaveUnderline, boolean isHaveStrikeThru) {
        paint.setFakeBoldText(isBold);
        paint.setUnderlineText(isHaveUnderline);
        paint.setStrikeThruText(isHaveStrikeThru);
        invalidate();
    }


}
