package com.chenyang.androidproject.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCharText(Character character) {
        setText(String.valueOf(character));
    }
}
