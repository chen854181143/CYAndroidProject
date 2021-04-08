package com.chenyang.androidproject.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.activity.AudioRecordingStudentActivity;
import com.chenyang.androidproject.activity.AutoScrollRecyclerViewActivity;
import com.chenyang.androidproject.activity.CalendarViewStudentActivity;
import com.chenyang.androidproject.activity.ConstraintLayoutStudentActivity;
import com.chenyang.androidproject.activity.CustomViewStudentActivity;
import com.chenyang.androidproject.activity.EditTextFieldStudentActivity;
import com.chenyang.androidproject.activity.ExpandableTextViewStudentActivity;
import com.chenyang.androidproject.activity.FrescoStudentActivity;
import com.chenyang.androidproject.activity.IconifyStudentActivity;
import com.chenyang.androidproject.activity.ImageAddWatermarkActivity;
import com.chenyang.androidproject.activity.ItemDecorationStudentListActivity;
import com.chenyang.androidproject.activity.KeyboardStudentListActivity;
import com.chenyang.androidproject.activity.LottieStudentActivity;
import com.chenyang.androidproject.activity.MPAndroidChartStudentActivity;
import com.chenyang.androidproject.activity.MPAndroidChartStudentRouteActivity;
import com.chenyang.androidproject.activity.NFCStudentActivity;
import com.chenyang.androidproject.activity.RecyclerViewAndEdittextStudentActivity;
import com.chenyang.androidproject.activity.RxjavaAndRetrofitStudentActivity;
import com.chenyang.androidproject.activity.VideocacheStudentActivity;
import com.chenyang.androidproject.activity.WebViewUploadFileActivity;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.utils.LogUtils;
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

    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.recyclerview_student)
    RecyclerView mRecyclerView;
    private List<String> listStudent;
    private long lastCurrentTime;

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
                } else if (position == 10) {
                    startActivity(ConstraintLayoutStudentActivity.class);
                } else if (position == 11) {
                    startActivity(AudioRecordingStudentActivity.class);
                } else if (position == 12) {
                    startActivity(VideocacheStudentActivity.class);
                } else if (position == 13) {
                    startActivity(AutoScrollRecyclerViewActivity.class);
                } else if (position == 14) {
                    startActivity(CalendarViewStudentActivity.class);
                } else if (position == 15) {
                    startActivity(NFCStudentActivity.class);
                } else if (position == 16) {
                    startActivity(RecyclerViewAndEdittextStudentActivity.class);
                } else if (position == 17) {
                    startActivity(ImageAddWatermarkActivity.class);
                } else if (position == 18) {
                    studyCurrentTimeMillis();
                } else if (position == 19) {
                    startActivity(KeyboardStudentListActivity.class);
                } else if (position == 20) {
                    startActivity(ItemDecorationStudentListActivity.class);
                }
            }
        });
    }

    private void studyCurrentTimeMillis() {
        if (lastCurrentTime == 0) {
            lastCurrentTime = System.currentTimeMillis();
            return;
        }
        int totalTime = (int) (System.currentTimeMillis() - lastCurrentTime);
        lastCurrentTime = System.currentTimeMillis();
        toast("时间间隔:" + totalTime + "毫秒");
        LogUtils.debug(TAG, String.valueOf(totalTime));
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