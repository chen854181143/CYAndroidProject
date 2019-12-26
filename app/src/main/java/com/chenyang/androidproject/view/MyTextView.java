package com.chenyang.androidproject.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCharText(Character character) {
        setText(String.valueOf(character));
    }
}
