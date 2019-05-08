package com.chenyang.androidproject.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.NewsBean;
import com.chenyang.androidproject.utils.GlideOption;
import com.sunfusheng.GlideImageView;
import com.sunfusheng.progress.CircleProgressView;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<NewsBean.ResultBean.DataBean, BaseViewHolder> {

    public NewsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean.ResultBean.DataBean item) {
        GlideImageView ivNews = helper.getView(R.id.iv_image);
        CircleProgressView progressView = helper.getView(R.id.progressView);
        helper.setText(R.id.tv_title, item.getTitle());

        ivNews.fitCenter()
//                .error(R.mipmap.image_load_err)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(GlideOption.options)
                .load(item.getAlbums().get(0), R.color.placeholder, (isComplete, percentage, bytesRead, totalBytes) -> {
                    if (isComplete) {
                        progressView.setVisibility(View.GONE);
                    } else {
                        progressView.setVisibility(View.VISIBLE);
                        progressView.setProgress(percentage);
                    }
                });

    }
}
