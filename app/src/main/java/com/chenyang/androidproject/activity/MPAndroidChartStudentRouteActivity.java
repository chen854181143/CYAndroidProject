package com.chenyang.androidproject.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MPAndroidChartStudentRouteActivity extends MyActivity {

    @BindView(R.id.recyclerview_student)
    RecyclerView mRecyclerView;
    private List<String> listStudent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mp_android_chart_student_route;
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
        listStudent = new ArrayList<String>();
        String[] students = getResources().getStringArray(R.array.mp_android_chart_student);
        for (String name : students) {
            listStudent.add(name);
        }
        DialogAdapter dialogAdapter = new DialogAdapter(R.layout.item_dialog_users, listStudent);
        //添加默认分割线：高度为2px，颜色为灰色
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setAdapter(dialogAdapter);
        dialogAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    startActivity(MPAndroidChartStudentOneActivity.class);
                }else if (position == 1) {
                    startActivity(MPAndroidChartStudentTwoActivity.class);
                }else if (position == 2) {
                    startActivity(MPAndroidChartStudentThreeActivity.class);
                }
            }
        });
    }
}
