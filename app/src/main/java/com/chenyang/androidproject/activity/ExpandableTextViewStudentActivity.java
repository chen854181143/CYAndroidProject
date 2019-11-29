package com.chenyang.androidproject.activity;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.CustomTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;

/**
 * ExpandableTextView(一个可折叠的Textview)
 * 自定义TextView
 */
public class ExpandableTextViewStudentActivity extends MyActivity {
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.ctv_test)
    CustomTextView ctvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expandabletextview_student;
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
        expandTextView.setText(getString(R.string.dummy_text));
        ctvTest.setStyle(false, true, false);
    }


}
