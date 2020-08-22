package com.chenyang.androidproject.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ConstraintLayoutStudentActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraintlayout_student;
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

    }
}
