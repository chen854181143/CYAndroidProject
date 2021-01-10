package com.chenyang.androidproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.chenyang.androidproject.adapter.global.GlobalAdapter;
import com.chenyang.androidproject.base.BaseFragmentAdapter;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.eventbus.ConstantEvent;
import com.chenyang.androidproject.fragment.TestFragmentA;
import com.chenyang.androidproject.fragment.TestFragmentB;
import com.chenyang.androidproject.fragment.TestFragmentC;
import com.chenyang.androidproject.fragment.TestFragmentD;
import com.chenyang.androidproject.helper.ActivityStackManager;
import com.chenyang.androidproject.helper.DoubleClickHelper;
import com.chenyang.androidproject.view.gloading.Gloading;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.waveswipe.DropBounceInterpolator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.jzvd.Jzvd;

public class MainActivity extends MyActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private BaseFragmentAdapter<MyLazyFragment> mPagerAdapter;
    //返回code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
    };

    //初始化SmartRefreshLayout
    //static 代码段可以防止内存泄露
    static {
        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
                layout.setEnableHeaderTranslationContent(false);
                return new MaterialHeader(context).setColorSchemeResources(R.color.red,R.color.green,R.color.blue);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

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
        ToastUtils.setGravity(Gravity.BOTTOM,0,200);
        // 初始化Gloading框架
        Gloading.debug(BuildConfig.DEBUG);
        Gloading.initDefault(new GlobalAdapter());

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
        //只有当前不是全屏模式才做指定的操作
        if(Jzvd.currentScreen!=Jzvd.SCREEN_WINDOW_FULLSCREEN){
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
                if (Jzvd.backPress()) {
                    return;
                }
            }
        }else if(Jzvd.currentScreen==Jzvd.SCREEN_WINDOW_FULLSCREEN){
            if (Jzvd.backPress()) {
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);

        
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        if (lacksPermission()) {//判断是否拥有权限
            //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {
            //拥有权限执行操作

        }
    }

    //如果返回true表示缺少权限
    public boolean lacksPermission() {
        for (String permission : permissions) {
            //判断是否缺少权限，true=缺少权限
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {//响应Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "未拥有相应权限", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //拥有权限执行操作

                } else {
                    Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
