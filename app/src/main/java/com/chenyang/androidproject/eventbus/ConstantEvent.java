package com.chenyang.androidproject.eventbus;

public class ConstantEvent {
    private int type;
    //监听返回键
    public static final int BACKBUTTONCLICK=1;
    public ConstantEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
