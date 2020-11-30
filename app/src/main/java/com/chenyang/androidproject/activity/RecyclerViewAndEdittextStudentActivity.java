package com.chenyang.androidproject.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.EdittextAdapter;
import com.chenyang.androidproject.bean.EdittextBean;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * RecyclerView中使用EditText
 */
public class RecyclerViewAndEdittextStudentActivity extends MyActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view_and_edittext;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        List datas = new ArrayList<EdittextBean>();
        for (int i = 0; i < 20; i++) {
            datas.add(new EdittextBean());
        }
        EdittextAdapter adapter = new EdittextAdapter(R.layout.item_edittext, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);//设置这个属性 Compatibility support for {@linkandroid.widget.AbsListView#setStackFromBottom(boolean)}
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
