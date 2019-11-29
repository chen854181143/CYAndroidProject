package com.chenyang.androidproject.activity;

import android.view.View;
import android.widget.Toast;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.view.AmountView;
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
    @BindView(R.id.amount_view)
    AmountView mAmountView;

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

        mAmountView = (AmountView) findViewById(R.id.amount_view);
        mAmountView.setGoods_storage(50);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                Toast.makeText(getApplicationContext(), "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
