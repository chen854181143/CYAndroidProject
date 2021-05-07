package com.chenyang.androidproject.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.base.BaseActivity;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.capture.CameraPreview;
import com.chenyang.androidproject.utils.capture.CameraUtil;
import com.chenyang.androidproject.utils.capture.CircleCameraLayout;
import com.chenyang.androidproject.utils.capture.Constants;
import com.chenyang.androidproject.utils.capture.GameView;
import com.chenyang.androidproject.utils.capture.ToolsFile;
import com.chenyang.androidproject.utils.capture.TransUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class FaceActivity extends Activity implements CameraPreview.OnPreviewFrameListener, CameraPreview.OnFaceIdentifyListener, View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 10;
    private String[] mPermissions = {Manifest.permission.CAMERA};
    private boolean isgetFace = false;
    private boolean hasPermissions;
    private boolean resume = false;
    TextView moduleface_tv_hint, moduleface_tv_remake, moduleface_tv_name, moduleface_tv_department, moduleface_tv_back, moduleface_tv_commit, moduleface_tv_cancel;
    LinearLayout moduleface_ll_commitorcancle;
    CircleCameraLayout mCameraLayout;
    CameraPreview mCameraPreview;
    private CountDownTimer mBackCountTimer;
    private Context mContext;
    private int time;
    //该组数据无效，仅做示例参考
    public static String APP_ID = "5TULcP6vf4kRE3mhZcpcD8x6S9T24koUrikNB4ryXvni";
    public static String SDK_KEY = "5qetXwPEQByWDfBsCY2b819CGnJaFynTqM4jdcmh8mAL";
    public static String ACTIVE_KEY = "8571-116W-3132-ZXAW";
    private String TAG = getClass().getSimpleName();
    private RelativeLayout rl_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSPARENT);
        setContentView(R.layout.face_activity_launch);
        mContext = this;
        activeOnline();
        if (Constants.faceEngine == null) {
            faceInit();
        }
        initView();
        initData();
        if (CameraUtil.checkPermissionAllGranted(this, mPermissions)) {
            hasPermissions = true;
        } else {
            ActivityCompat.requestPermissions(this, mPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void initView() {
        mCameraLayout = findViewById(R.id.moduleface_circlecamera_layout);
        moduleface_tv_hint = findViewById(R.id.moduleface_tv_hint);
        moduleface_tv_remake = findViewById(R.id.moduleface_tv_remake);
        moduleface_tv_name = findViewById(R.id.moduleface_tv_name);
        moduleface_tv_department = findViewById(R.id.moduleface_tv_department);
        moduleface_tv_back = findViewById(R.id.moduleface_tv_back);
        moduleface_tv_commit = findViewById(R.id.moduleface_tv_commit);
        moduleface_tv_cancel = findViewById(R.id.moduleface_tv_cancel);
        moduleface_ll_commitorcancle = findViewById(R.id.moduleface_ll_commitorcancle);
        rl_top = findViewById(R.id.rl_top);
        moduleface_tv_back.setOnClickListener(this);
        moduleface_tv_cancel.setOnClickListener(this);
        moduleface_tv_commit.setOnClickListener(this);
        moduleface_tv_remake.setOnClickListener(this);
    }

    private void initData() {
        moduleface_tv_hint.setText("请您面向屏幕,开始刷脸");
    }

    private void startCamera() {
        if (mCameraPreview != null) {
            mCameraPreview.releaseCamera();
        }
        mCameraPreview = new CameraPreview(this, this);
        mCameraPreview.setOnFaceIdentifyListener(this);
        mCameraLayout.removeAllViews();
        if (mCameraPreview.hasCamera) {
            mCameraLayout.setCameraPreview(mCameraPreview);
            if (!hasPermissions || resume) {
                mCameraLayout.startView();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCameraPreview.startPreview();
                }
            }, 2000);
        } else {
            if (moduleface_tv_hint != null) {
                moduleface_tv_hint.setText("没有检测到摄像头");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPermissions) {
            startCamera();
            resume = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCameraPreview) {
            mCameraPreview.releaseCamera();
        }
        mCameraLayout.release();
    }

    /**
     * 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            for (int grant : grantResults) {  // 判断是否所有的权限都已经授予了
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) { // 所有的权限都授予了
                startCamera();
            } else {// 提示需要权限的原因
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("拍照需要允许权限, 是否再次开启?")
                        .setTitle("提示")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(FaceActivity.this, mPermissions, PERMISSION_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder.create().show();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onPreviewFrame(Bitmap bitmap) {
        if (!isgetFace) {
            try {
                isgetFace = true;
                String fileDir = Environment.getExternalStorageDirectory() + "/etc/facePic/";
                String fileName = System.currentTimeMillis() + ".jpg";
                String path = fileDir + fileName;
                ToolsFile.delAllFile(fileDir);
                if (!new File(fileDir).exists()) {
                    new File(fileDir).mkdirs();
                }
                TransUtil.bitmap2File(bitmap, path);
                String img = TransUtil.fileToBase64(new File(path));
                TransUtil.saveFile(img, Environment.getExternalStorageDirectory().toString() + "/etc/face.txt");
                if (null != mCameraPreview) {
                    mCameraPreview.releaseCamera();
                }
                mCameraLayout.release();
                moduleface_tv_hint.setText("请稍后,正在处理");
                moduleface_tv_back.setVisibility(View.GONE);
                if (mBackCountTimer != null) {
                    mBackCountTimer.cancel();
                    mBackCountTimer = null;
                }
                isgetFace = false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isgetFace = false;
            }
        }
    }

    /**
     * 初始化
     */
    private void faceInit() {
        int scale = 32;
        int maxFaceNum = 1;
        // 如下的组合，初始化的功能包含：人脸检测、人脸识别、RGB活体检测、年龄、性别、人脸3D角度
        int initMask = FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS | FaceEngine.ASF_AGE |
                FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_IR_LIVENESS;
        FaceEngine faceEngine = new FaceEngine();
        int code = faceEngine.init(getApplicationContext(),
                DetectMode.ASF_DETECT_MODE_VIDEO, DetectFaceOrientPriority.ASF_OP_270_ONLY, scale,
                maxFaceNum, initMask);
        if (code != ErrorInfo.MOK) {
            Log.i(TAG, "init failed, code is : " + code);
        } else {
            Log.i(TAG, "init success");
            Constants.faceEngine = faceEngine;
        }
    }

    /**
     * 在线激活
     */
    private void activeOnline() {
        int code = FaceEngine.activeOnline(getApplicationContext(), ACTIVE_KEY, APP_ID, SDK_KEY);
        if (code == ErrorInfo.MOK) {
            Log.i(TAG, "activeOnline success");
        } else if (code == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
            Log.i(TAG, "already activated");
        } else {
            Log.i(TAG, "activeOnline failed, code is : " + code);
        }
    }

    @Override
    public void onSuccess(Rect rect) {
        GameView gameView = new GameView(this, rect);
        if (rl_top != null) {
            rl_top.addView(gameView);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
