package com.chenyang.androidproject.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.NewsAdapter;
import com.chenyang.androidproject.adapter.SmallVideoAdapter;
import com.chenyang.androidproject.bean.VideoBean;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.utils.RecycleViewDivider;
import com.chenyang.androidproject.view.gloading.Gloading;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jzvd.Jzvd;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目框架使用示例
 */
public class TestFragmentC extends MyLazyFragment {

    @BindView(R.id.recyclerview_video)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private SmallVideoAdapter smallVideoAdapter;
    private List<VideoBean> videoBeanList;
    private boolean isFirstEnter = true;
    private Gloading.Holder holder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
       /* holder = Gloading.getDefault().wrap(mRefreshLayout).withRetry(new Runnable() {
            @Override
            public void run() {
                holder.showLoading();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });*/
        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });

        videoBeanList = new ArrayList<>();
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v2.yongjiujiexi.com/20170917/e1bvFGAX/index.m3u8", "第496集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v8.yongjiu8.com/20171111/WODmxBKz/index.m3u8", "第500集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v8.yongjiu8.com/20171111/bfxmTPpK/index.m3u8", "第501集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yong.yongjiu6.com/20171119/bGN3AECN/index.m3u8", "第502集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yong.yongjiu6.com/20171125/tBKKm4Cu/index.m3u8", "第503集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yong.yongjiu6.com/20171202/TnAv6hpi/index.m3u8", "第504集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yong.yongjiu6.com/20180102/6iKmsswx/index.m3u8", "第505集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180109/wp1uq46l/index.m3u8", "第506集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180114/e3eUPe8N/index.m3u8", "第507集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180120/CUbqEcLe/index.m3u8", "第508集"));


        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180127/jjAnFrNu/index.m3u8", "第509集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180204/3kfdoQoR/index.m3u8", "第510集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180210/8bwl3CxZ/index.m3u8", "第511集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://yj.yongjiu6.com/20180218/YBZknFJg/index.m3u8", "第512集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v2.yongjiujiexi.com/20180225/rfCgo6FT/index.m3u8", "第513集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v3.yongjiujiexi.com/20180408/og9VnD5b/index.m3u8", "第515集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v3.yongjiujiexi.com/20180408/hu1xVD2w/index.m3u8", "第516集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v3.yongjiujiexi.com/20180417/y1WCWDvw/index.m3u8", "第517集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v3.yongjiujiexi.com/20180422/8wsZU5Px/index.m3u8", "第518集"));
        videoBeanList.add(new VideoBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557311585670&di=4658ee8dc8a6dff504f4ae385c9d386b&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F4ff6d6479d11477abbcef9bf4e944de0db78fcec.jpg", "https://v3.yongjiujiexi.com/20180429/YnSQ5baH/index.m3u8", "第519集"));
        smallVideoAdapter = new SmallVideoAdapter(R.layout.item_small_video, videoBeanList);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray1)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
//        smallVideoAdapter.openLoadAnimation();
//        smallVideoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);

        mRecyclerView.setAdapter(smallVideoAdapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.jz_video);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.resetAllVideos();
                    }
                }
            }
        });
    }


    public static TestFragmentC newInstance() {
        return new TestFragmentC();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isStatusBarEnabled() && isLazyLoad()) {
            // 重新初始化状态栏
            statusBarConfig().init();
        }
        if (!isVisibleToUser) {
            Jzvd.resetAllVideos();
        }
    }

}