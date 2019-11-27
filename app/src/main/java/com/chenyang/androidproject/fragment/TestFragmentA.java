package com.chenyang.androidproject.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.adapter.DialogAdapter;
import com.chenyang.androidproject.base.BaseDialog;
import com.chenyang.androidproject.base.BaseDialogFragment;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.common.MyLazyFragment;
import com.chenyang.androidproject.dialog.AddressDialog;
import com.chenyang.androidproject.dialog.DateDialog;
import com.chenyang.androidproject.dialog.InputDialog;
import com.chenyang.androidproject.dialog.MenuDialog;
import com.chenyang.androidproject.dialog.MessageDialog;
import com.chenyang.androidproject.dialog.PayPasswordDialog;
import com.chenyang.androidproject.dialog.ToastDialog;
import com.chenyang.androidproject.dialog.WaitDialog;
import com.chenyang.androidproject.utils.RecycleViewDivider;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目炫酷效果示例
 */
public class TestFragmentA extends MyLazyFragment {

    @BindView(R.id.recyclerview_dialog)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_test_address)
    TextView tvTestAddress;
    @BindView(R.id.tv_test_search)
    TextView tvTestSearch;
    @BindView(R.id.tb_test_a_bar)
    TitleBar tbTestABar;

    private List<String> listDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
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
        listDialog = new ArrayList<String>();
        String[] citys = getResources().getStringArray(R.array.dialog_user);
        for (String name : citys) {
            listDialog.add(name);
        }
        DialogAdapter dialogAdapter = new DialogAdapter(R.layout.item_dialog_users, listDialog);
        //添加默认分割线：高度为2px，颜色为灰色
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        //添加自定义分割线：可自定义分割线drawable
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                getContext(), LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));
        //添加自定义分割线：可自定义分割线高度和颜色
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                getContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray)));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setAdapter(dialogAdapter);
        dialogAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    new MessageDialog.Builder(getActivity())
                            .setTitle("我是标题") // 标题可以不用填写
                            .setMessage("我是内容")
                            .setConfirm("确定")
                            .setCancel("取消") // 设置 null 表示不显示取消按钮
                            .setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    toast("确定了");
                                    dialog.dismiss();
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (position == 1) {
                    new InputDialog.Builder(getActivity())
                            .setTitle("我是标题") // 标题可以不用填写
                            .setContent("我是内容")
                            .setHint("我是提示")
                            .setConfirm("确定")
                            .setCancel("取消") // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new InputDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog, String content) {
                                    toast("确定了：" + content);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                } else if (position == 2) {
                    List<String> data = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        data.add("我是数据" + i);
                    }
                    new MenuDialog.Builder(getActivity())
                            .setCancel("取消") // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setList(data)
                            .setListener(new MenuDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, int position, String text) {
                                    toast("位置：" + position + "，文本：" + text);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .setGravity(Gravity.BOTTOM)
                            .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                            .show();
                } else if (position == 3) {
                    List<String> data1 = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        data1.add("我是数据" + i);
                    }
                    new MenuDialog.Builder(getActivity())
                            .setCancel(null) // 设置 null 表示不显示取消按钮
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setList(data1)
                            .setListener(new MenuDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, int position, String text) {
                                    toast("位置：" + position + "，文本：" + text);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .setGravity(Gravity.CENTER)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            .show();
                } else if (position == 4) {
                    new ToastDialog.Builder(getActivity())
                            .setType(ToastDialog.Type.FINISH)
                            .setMessage("完成")
                            .show();
                } else if (position == 5) {
                    new ToastDialog.Builder(getActivity())
                            .setType(ToastDialog.Type.ERROR)
                            .setMessage("错误")
                            .show();
                } else if (position == 6) {
                    new ToastDialog.Builder(getActivity())
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("警告")
                            .show();
                } else if (position == 7) {
                    final BaseDialog dialog = new WaitDialog.Builder(getActivity())
                            .setMessage("加载中...") // 消息文本可以不用填写
                            .show();
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 3000);
                } else if (position == 8) {
                    new PayPasswordDialog.Builder(getActivity())
                            .setTitle("请输入支付密码")
                            .setSubTitle("用于购买一个女盆友")
                            .setMoney("￥ 100.00")
                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                            .setListener(new PayPasswordDialog.OnListener() {

                                @Override
                                public void onCompleted(Dialog dialog, String password) {
                                    toast(password);

                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                } else if (position == 9) {
                    new AddressDialog.Builder(getActivity())
                            .setTitle("选择地区")
                            //.setIgnoreArea() // 不选择县级区域
                            .setListener(new AddressDialog.OnListener() {

                                @Override
                                public void onSelected(Dialog dialog, String province, String city, String area) {
                                    toast(province + city + area);
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                } else if (position == 10) {
                    new DateDialog.Builder(getActivity())
                            .setTitle("请选择日期")
                            .setListener(new DateDialog.OnListener() {
                                @Override
                                public void onSelected(Dialog dialog, int year, int month, int day) {
                                    toast(year + "年" + month + "月" + day + "日");
                                }

                                @Override
                                public void onCancel(Dialog dialog) {
                                    toast("取消了");
                                }
                            })
                            .show();
                } else if (position == 11) {
                    new BaseDialogFragment.Builder(getActivity())
                            .setContentView(R.layout.dialog_custom)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            //.setText(id, "我是预设置的文本")
                            .setOnClickListener(R.id.btn_dialog_custom_ok, new BaseDialog.OnClickListener<ImageView>() {

                                @Override
                                public void onClick(Dialog dialog, ImageView view) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (position == 12) {
                    new BaseDialogFragment.Builder(getActivity())
                            .setContentView(R.layout.dialog_call_custom_phone)
                            .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                            //.setText(id, "我是预设置的文本")
                            .setOnClickListener(R.id.quxiao, new BaseDialog.OnClickListener<LinearLayout>() {
                                @Override
                                public void onClick(Dialog dialog, LinearLayout view) {
                                    toast("取消");
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.queding, new BaseDialog.OnClickListener<LinearLayout>() {
                                @Override
                                public void onClick(Dialog dialog, LinearLayout view) {
                                    toast("确定");
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (position == 13) {
                    Snackbar.make(getView(), "测试一下!", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getActivity().getResources().getColor(R.color.red)).show();
                }
            }
        });
    }

    public static TestFragmentA newInstance() {
        return new TestFragmentA();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}