package com.chenyang.androidproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.chenyang.androidproject.base.BaseFragmentAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.fragment.TestFragmentA;
import com.chenyang.androidproject.fragment.TestFragmentB;
import com.chenyang.androidproject.fragment.TestFragmentC;
import com.chenyang.androidproject.fragment.TestFragmentD;
import com.chenyang.androidproject.helper.ActivityStackManager;
import com.chenyang.androidproject.helper.DoubleClickHelper;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;

public class MainActivity extends MyActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private BaseFragmentAdapter<MyLazyFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.addOnPageChangeListener(this);
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        // 初始化吐司工具类
        ToastUtils.init(getApplication());

        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(TestFragmentA.newInstance());
        mPagerAdapter.addFragment(TestFragmentB.newInstance());
        mPagerAdapter.addFragment(TestFragmentC.newInstance());
        mPagerAdapter.addFragment(TestFragmentD.newInstance());
        mViewPager.setAdapter(mPagerAdapter);
        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                mViewPager.setCurrentItem(0, mViewPager.getCurrentItem() == 1);
                return true;
            case R.id.home_found:
                mViewPager.setCurrentItem(1, mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 2);
                return true;
            case R.id.home_message:
                mViewPager.setCurrentItem(2, mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 3);
                return true;
            case R.id.home_me:
                mViewPager.setCurrentItem(3, mViewPager.getCurrentItem() == 2);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_message);
                break;
            case 3:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 返回键-退出应用
     */
    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getHandler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

}
