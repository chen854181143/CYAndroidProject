package com.chenyang.androidproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chenyang.androidproject.R;

/**
 * 根据layout中子View的位置，确定局部透明区域
 * Created by juan on 2018/07/20.
 */
public class CustomLayout extends FrameLayout {

    private Context mContext;
    private CustomDrawable background;

    public CustomLayout(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs, 0);
    }

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        background = new CustomDrawable(getBackground());
        setBackground(background);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetBackgroundHoleArea();
    }

    @SuppressLint("NewApi")
    private void resetBackgroundHoleArea() {
        Path path = null;
        // 以子View为范围构造需要透明显示的区域
        View view = findViewById(R.id.iv_scan);
        if (view != null) {
            path = new Path();
            // 矩形透明区域
//            path.addRoundRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), dp2Px(mContext,10), dp2Px(mContext,10),Path.Direction.CW);
            path.addCircle(getWidth() / 2, getHeight() / 2, view.getWidth() / 2, Path.Direction.CW);
        }
        if (path != null) {
            background.setSrcPath(path);
        }
    }

    public int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
