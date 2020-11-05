package com.chenyang.androidproject.activity;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.NoticeRecyclerViewAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.MeasureUtil;
import com.chenyang.androidproject.view.AutoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * RecyclerView实现垂直自动无限滚动，类似于中奖信息，跑马灯
 */
public class AutoScrollRecyclerViewActivity extends MyActivity {

    @BindView(R.id.ll_recycler)
    LinearLayoutCompat mLlRecycler;

    private AutoScrollRecyclerView mRv1;
    private List<String> data;
    private NoticeRecyclerViewAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_scroll_recyclerview;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        ViewTreeObserver vto = mLlRecycler.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int heigh = mLlRecycler.getHeight();
                mLlRecycler.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add("测试数据" + i);
        }

        adapter = new NoticeRecyclerViewAdapter(data);
        mRv1 = findViewById(R.id.am_rv1);
        mRv1.setLayoutManager(new LinearLayoutManager(this));
        mRv1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRv1.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //流式滚动效果
        mRv1.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRv1.stop();
    }

    public void btnPause(View view) {
        mRv1.stop();
    }

    public void btnResume(View view) {
        mRv1.start();
    }

    public void changeData(View view) {
        mRv1.stop();
        data.clear();
        for (int i = 0; i < 13; i++) {
            data.add("数据改变" + i);
        }
        mRv1.setAdapter(adapter);
        mRv1.start();
    }

}
