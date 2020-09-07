package com.chenyang.androidproject.view.customview;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class FallingBallImageView extends AppCompatImageView {

    public FallingBallImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFallingPos(Point pos) {
        layout(pos.x, pos.y, pos.x + getWidth(), pos.y + getHeight());
    }
}
