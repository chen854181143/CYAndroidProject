package com.chenyang.androidproject.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.NewsAdapter;
import com.chenyang.androidproject.bean.NewsBean;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.okHttp.ApiService;
import com.chenyang.androidproject.okHttp.CallBackUtil;
import com.chenyang.androidproject.view.gloading.Gloading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.bar.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目自定义控件展示
 */
public class TestFragmentB extends MyLazyFragment {

    @BindView(R.id.tb_test_a_bar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerview_news)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<NewsBean.ResultBean.DataBean> listNews=new ArrayList<>();
    private NewsAdapter newsAdapter;
    private Gloading.Holder holder;
    private int page;
    private boolean isFirstEnter = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
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
        holder = Gloading.getDefault().wrap(mRefreshLayout).withRetry(new Runnable() {
            @Override
            public void run() {
                holder.showLoading();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        requestNews("0",mRefreshLayout);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });
        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                page=0;
                requestNews("1",refreshLayout);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page+=10;
                requestNews("2",refreshLayout);
            }
        });
        requestNews("0",mRefreshLayout);
    }

    public static TestFragmentB newInstance() {
        return new TestFragmentB();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
    }

    @Override
    public void onTitleClick(View v) {
        super.onTitleClick(v);
        toast("点击标题了");
    }

    /**
     *
     * @param tag 0 正在加载  1 下拉刷新 2 加载更多
     */
    private void requestNews(final String tag, final RefreshLayout refreshLayout){
        ApiService instance = ApiService.Instance();
        HashMap<String, String> map = new HashMap<>();
        map.put("menu", "土豆");
        map.put("dtype", "json");
        map.put("rn", "10");
        map.put("pn", page+"");
        map.put("key","f284c71f3210536e8fff3feae4be17a7");
        instance.okHttpPost("http://apis.juhe.cn/cook/query.php", map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toast(e.getMessage());
                holder.showLoadFailed();
                if(tag.equals("1")){
                    refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                }else if(tag.equals("2")){
                    refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                }
            }

            @Override
            public void onResponse(String response) {
                try{
                    Gson gson = new Gson();
                    NewsBean bean = gson.fromJson(response, NewsBean.class);
                    String code = bean.getResultcode();
                    if (code.equals("200")) {
                        holder.showLoadSuccess();
                        if(tag.equals("1")){
                            listNews.clear();
                            refreshLayout.finishRefresh();
                        }else if(tag.equals("2")){
//                          refreshLayout.finishLoadMoreWithNoMoreData();
                            refreshLayout.finishLoadMore();
                        }
                        listNews.addAll(bean.getResult().getData());
                        if(newsAdapter==null){
                            newsAdapter = new NewsAdapter(R.layout.item_news,listNews);
                            newsAdapter.openLoadAnimation();
                            //设置自定义动画
                            newsAdapter.openLoadAnimation(new BaseAnimation() {
                                @Override
                                public Animator[] getAnimators(View view) {
                                    return new Animator[]{
                                            ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                                            ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                                    };
                                }
                            });
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
                            mRecyclerView.setAdapter(newsAdapter);
                        }else{
                            newsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        toast(bean.getReason());
                        holder.showLoadFailed();
                        if(tag.equals("1")){
                            refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                        }else if(tag.equals("2")){
                            refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    toast("数据解析异常");
                    holder.showLoadFailed();
                    if(tag.equals("1")){
                        refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                    }else if(tag.equals("2")){
                        refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                    }
                }
            }
        });
    }



}