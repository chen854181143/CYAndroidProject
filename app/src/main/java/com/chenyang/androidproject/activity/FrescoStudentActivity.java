package com.chenyang.androidproject.activity;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.fresco.FrescoImageView;

import butterknife.BindView;

/**
 * Fresco图片加载框架的学习
 * 网址:https://github.com/facebook/fresco
 */
public class FrescoStudentActivity extends MyActivity {
    @BindView(R.id.fresco1)
    FrescoImageView mFresco1;
    @BindView(R.id.fresco2)
    FrescoImageView mFresco2;
    @BindView(R.id.fresco3)
    FrescoImageView mFresco3;
    @BindView(R.id.fresco4)
    FrescoImageView mFresco4;
    @BindView(R.id.fresco5)
    FrescoImageView mFresco5;
    @BindView(R.id.fresco6)
    FrescoImageView mFresco6;

    @Override
    protected int getLayoutId() {
        return R.layout.fresco_student;
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
        mFresco1.setFadeIn(true);
        mFresco1.setImageUriByLp("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2912717011,2532015867&fm=26&gp=0.jpg");
        mFresco2.setImageUriByLp("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=366864449,772502783&fm=26&gp=0.jpg");
        mFresco3.setImageUriByLp("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3853609878,1970197198&fm=26&gp=0.jpg");
        mFresco4.setImageUriByLp("https://sasasasasaasss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1260267605,2212096157&fm=26&gp=0.jpgsasasas");
        mFresco5.setImageUriByLp("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1407433045,2151629447&fm=26&gp=0.jpg");
        mFresco6.setImageUriByLp("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=143186223,1314668154&fm=26&gp=0.jpg");
    }
}
