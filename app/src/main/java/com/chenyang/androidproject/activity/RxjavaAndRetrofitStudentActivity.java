package com.chenyang.androidproject.activity;

import android.util.Log;
import android.widget.TextView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.Demo;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.retrofit.BaseObserver;
import com.chenyang.androidproject.retrofit.MyObserver;
import com.chenyang.androidproject.retrofit.RequestUtils;
import com.chenyang.androidproject.retrofit.RetrofitUtils;
import com.chenyang.androidproject.retrofit.RxHelper;

import butterknife.BindView;

/**
 * Rxjava+Retrofit网络请求框架封装
 */
public class RxjavaAndRetrofitStudentActivity extends MyActivity {
    @BindView(R.id.tv_retrofit)
    TextView tv_retrofit;
    private static final String TAG = "RxjavaAndRetrofit";

    /**/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxjava_and_retrofit_student;
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
        RequestUtils.getDemo(this, new MyObserver<Demo>(this) {
            @Override
            public void onSuccess(Demo result) {
                tv_retrofit.setText(result.toString());
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                tv_retrofit.setText(errorMsg);
            }
        });
    }

}
