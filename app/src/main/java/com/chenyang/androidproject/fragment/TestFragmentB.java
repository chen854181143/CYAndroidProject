package com.chenyang.androidproject.fragment;

import android.view.View;
import android.widget.TextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyLazyFragment;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目自定义控件展示
 */
public class TestFragmentB extends MyLazyFragment {

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
        tv_name.setText("发现");
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
}