package com.chenyang.androidproject.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;

import java.util.List;

public class DialogAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public DialogAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.rtv_button_name,item)
              .addOnClickListener(R.id.rtv_button_name);
    }
}
