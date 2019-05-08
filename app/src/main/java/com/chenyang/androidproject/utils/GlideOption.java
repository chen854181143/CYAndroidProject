package com.chenyang.androidproject.utils;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chenyang.androidproject.R;

/**
 * glide的配置文件
 */
public class GlideOption {
    public static RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
            .error(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.ALL);//图片加载失败后，显示的图片

}
