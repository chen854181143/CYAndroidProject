package com.chenyang.androidproject.fragment;

import android.widget.TextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyLazyFragment;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目界面跳转示例
 */
public class TestFragmentD extends MyLazyFragment {

    @BindView(R.id.tv_name)
    TextView tv_name;

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
        tv_name.setText("我的");
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