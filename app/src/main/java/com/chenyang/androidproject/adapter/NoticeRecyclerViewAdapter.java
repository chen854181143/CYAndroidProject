package com.chenyang.androidproject.adapter;



import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenyang.androidproject.R;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/18
 * function  : 垂直滚动的RecyclerView的adapter
 */
public class NoticeRecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> data;
    public NoticeRecyclerViewAdapter(@Nullable List<String> data) {
        super(R.layout.item_txt, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.it_tv, item);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        if(data.size()<=12){
            return data.size();
        }else{
            return Integer.MAX_VALUE;
        }
    }


}
