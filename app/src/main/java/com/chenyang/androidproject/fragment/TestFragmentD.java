package com.chenyang.androidproject.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.activity.FrescoStudentActivity;
import com.chenyang.androidproject.activity.IconifyStudentActivity;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.base.BaseDialog;
import com.chenyang.androidproject.base.BaseDialogFragment;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.dialog.AddressDialog;
import com.chenyang.androidproject.dialog.DateDialog;
import com.chenyang.androidproject.dialog.InputDialog;
import com.chenyang.androidproject.dialog.MenuDialog;
import com.chenyang.androidproject.dialog.MessageDialog;
import com.chenyang.androidproject.dialog.PayPasswordDialog;
import com.chenyang.androidproject.dialog.ToastDialog;
import com.chenyang.androidproject.dialog.WaitDialog;
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
                }else if(position == 1){
                    startActivity(FrescoStudentActivity.class);
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