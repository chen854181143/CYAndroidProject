package com.chenyang.androidproject.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.EdittextBean;

import java.util.List;

public class EdittextAdapter extends BaseQuickAdapter<EdittextBean, BaseViewHolder> {

    public EdittextAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EdittextBean bean) {
        AppCompatEditText edittext = helper.getView(R.id.edittext);
        //1、为了避免TextWatcher在第2步被调用，提前将他移除。
        if (edittext.getTag() instanceof TextWatcher) {
            edittext.removeTextChangedListener((TextWatcher) edittext.getTag());
        }
        // 第2步：移除TextWatcher之后，设置EditText的Text。
        edittext.setText(bean.getValue());
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    bean.setValue("");
                } else {
                    bean.setValue(editable.toString());
                }
            }
        };
        edittext.addTextChangedListener(watcher);
        edittext.setTag(watcher);
    }

}
