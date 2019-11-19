package com.chenyang.androidproject.utils;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

public class FontModel implements IconFontDescriptor {
    /**
     * 返回下载的ttf文件
     * */
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    /**
     * 返回个icon类型数组
     *
     * */
    @Override
    public Icon[] characters() {
        return  IconModel.values();
    }
}
