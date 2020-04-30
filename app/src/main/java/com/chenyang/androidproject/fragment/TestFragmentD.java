package com.chenyang.androidproject.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.activity.CustomViewStudentActivity;
import com.chenyang.androidproject.activity.EditTextFieldStudentActivity;
import com.chenyang.androidproject.activity.ExpandableTextViewStudentActivity;
import com.chenyang.androidproject.activity.FrescoStudentActivity;
import com.chenyang.androidproject.activity.IconifyStudentActivity;
import com.chenyang.androidproject.activity.LottieStudentActivity;
import com.chenyang.androidproject.activity.MPAndroidChartStudentActivity;
import com.chenyang.androidproject.activity.MPAndroidChartStudentRouteActivity;
import com.chenyang.androidproject.activity.RxjavaAndRetrofitStudentActivity;
import com.chenyang.androidproject.activity.WebViewUploadFileActivity;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */
public class TestFragmentD extends MyLazyFragment {

    @BindView(R.id.recyclerview_student)
    RecyclerView mRecyclerView;
    private List<String> listStudent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_b_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        listStudent = new ArrayList<String>();
        String[] students = getResources().getStringArray(R.array.dialog_student);
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
                    startActivity(IconifyStudentActivity.class);
                } else if (position == 1) {
                    startActivity(FrescoStudentActivity.class);
                } else if (position == 2) {
                    startActivity(EditTextFieldStudentActivity.class);
                } else if (position == 3) {
                    startActivity(LottieStudentActivity.class);
                } else if (position == 4) {
                    startActivity(RxjavaAndRetrofitStudentActivity.class);
                } else if (position == 5) {
                    startActivity(ExpandableTextViewStudentActivity.class);
                } else if (position == 6) {
                    startActivity(MPAndroidChartStudentActivity.class);
                } else if (position == 7) {
                    startActivity(WebViewUploadFileActivity.class);
                } else if (position == 8) {
                    startActivity(CustomViewStudentActivity.class);
                } else if (position == 9) {
                    startActivity(MPAndroidChartStudentRouteActivity.class);
                }
            }
        });
    }

    public static TestFragmentD newInstance() {
        return new TestFragmentD();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}