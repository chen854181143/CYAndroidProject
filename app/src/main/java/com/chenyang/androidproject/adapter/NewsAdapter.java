package com.chenyang.androidproject.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.NewsBean;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<NewsBean.ResultBean.DataBean,BaseViewHolder>{

    public NewsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean.ResultBean.DataBean item) {
        ImageView ivNews=helper.getView(R.id.iv_image);
        helper.setText(R.id.tv_title,item.getTitle());
        Glide.with(mContext).load(item.getAlbums().get(0)).into(ivNews);
    }
}
