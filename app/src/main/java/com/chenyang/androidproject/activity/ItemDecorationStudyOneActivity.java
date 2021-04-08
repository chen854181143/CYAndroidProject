package com.chenyang.androidproject.activity;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.NoticeRecyclerViewAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.ColorDividerItemDecoration;
import com.chenyang.androidproject.utils.TimelineItemDecoration;
import com.chenyang.androidproject.view.AutoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ItemDecoration学习
 */
public class ItemDecorationStudyOneActivity extends MyActivity {

    private RecyclerView mRv1;
    private List<String> data;
    private NoticeRecyclerViewAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_itemdecoration_study_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add("测试数据" + i);
        }
        adapter = new NoticeRecyclerViewAdapter(data);
        mRv1 = findViewById(R.id.am_rv1);
        mRv1.setLayoutManager(new LinearLayoutManager(this));
//        mRv1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRv1.addItemDecoration(new ColorDividerItemDecoration());
//        mRv1.addItemDecoration(new TimelineItemDecoration(getContext()));
        mRv1.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
