package com.chenyang.androidproject.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.NewsBean;
import com.chenyang.androidproject.bean.VideoBean;
import com.chenyang.androidproject.jzvd.MyJzvdStd;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class SmallVideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    public SmallVideoAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        JzvdStd jzvdStd = helper.getView(R.id.jz_video);
        jzvdStd.setUp(
                item.getVideoUrl(),
                item.getVideoTitle(), Jzvd.SCREEN_NORMAL);
        Glide.with(mContext).load(item.getCoverImage()).into(jzvdStd.thumbImageView);
    }
}
