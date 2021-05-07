package com.chenyang.androidproject.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.capture.CameraPreview;
import com.chenyang.androidproject.utils.capture.Constants;
import com.chenyang.androidproject.utils.capture.GameView;
import com.chenyang.androidproject.view.CameraGLSurfaceView;
import com.chenyang.androidproject.view.FaceDeteView;
import com.chenyang.androidproject.view.FaceRectView;
import com.hjq.toast.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CameraPreviewStudyActivity extends MyActivity implements Camera.PreviewCallback {

    @BindView(R.id.sf_camera)
    CameraGLSurfaceView mSurfaceView;
    @BindView(R.id.rl_camera)
    RelativeLayout rl_camera;
    @BindView(R.id.faceView)
    FaceRectView faceView;

    private SurfaceHolder mSurfaceHolder;
    //摄像头Id 默认后置 0,前置的值是1
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private String TAG = getClass().getSimpleName();
    //该组数据无效，仅做示例参考
    String APP_ID = "5TULcP6vf4kRE3mhZcpcD8x6S9T24koUrikNB4ryXvni";
    String SDK_KEY = "5qetXwPEQByWDfBsCY2b819CGnJaFynTqM4jdcmh8mAL";
    String ACTIVE_KEY = "8571-116W-3132-ZXAW";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_preview_study;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        activeOnline();
        faceInit();

        mSurfaceHolder = mSurfaceView.getHolder();
        init();
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化增加回调
     */
    private void init() {
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //surface创建时执行
                if (mCamera == null) {
                    // mCameraId是后置还是前置 0是后置 1是前置
                    openCamera(mCameraId);
                }
                //并设置预览
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //surface绘制时执行
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //surface销毁时执行
                releaseCamera();
            }
        });
    }

    /**
     * 打开相机 并且判断是否支持该摄像头
     *
     * @param FaceOrBack 前置还是后置
     * @return
     */
    private boolean openCamera(int FaceOrBack) {
        //是否支持前后摄像头
        boolean isSupportCamera = isSupport(FaceOrBack);
        //如果支持
        if (isSupportCamera) {
            try {
                mCamera = Camera.open(FaceOrBack);
                initParameters(mCamera);
                //设置预览回调
                if (mCamera != null) {
                    mCamera.setPreviewCallback(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.show("打开相机失败~");
                return false;
            }
        }
        return isSupportCamera;
    }


    /**
     * 设置相机参数
     *
     * @param camera
     */
    private void initParameters(Camera camera) {
        try {
            //获取Parameters对象
            mParameters = camera.getParameters();
            //设置预览格式
            mParameters.setPreviewFormat(ImageFormat.NV21);
            //判断是否支持连续自动对焦图像
            if (isSupportFocus(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                //判断是否支持单次自动对焦
            } else if (isSupportFocus(Camera.Parameters.FOCUS_MODE_AUTO)) {
                //自动对焦(单次)
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            //给相机设置参数
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("初始化相机失败");
        }
    }

    /**
     * 判断是否支持某个相机
     *
     * @param faceOrBack 前置还是后置
     * @return
     */
    private boolean isSupport(int faceOrBack) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            //返回相机信息
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == faceOrBack) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否支持对焦模式
     *
     * @return
     */
    private boolean isSupportFocus(String focusMode) {
        boolean isSupport = false;
        //获取所支持对焦模式
        List<String> listFocus = mParameters.getSupportedFocusModes();
        for (String s : listFocus) {
            //如果存在 返回true
            if (s.equals(focusMode)) {
                isSupport = true;
            }

        }
        return isSupport;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        face(bytes, camera);
    }

    /**
     * Activity 销毁回调方法 释放各种资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    /**
     * 释放相机资源
     */
    public void releaseCamera() {
        if (mCamera != null) {
            //停止预览
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            //根据所传入的SurfaceHolder对象来设置实时预览
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //调整预览角度
            setCameraDisplayOrientation(this, mCameraId, mCamera);
            mCamera.startPreview();
            //这里同时开启人脸检测
//            startFaceDetect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 人脸检测
     */
    private void startFaceDetect() {
        //开始人脸识别，这个要调用startPreview之后调用
        mCamera.startFaceDetection();
        //添加回调
        mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
//                mCameraCallBack.onFaceDetect(transForm(faces), camera);
                Log.d("sssd", "检测到" + faces.length + "人脸");
            }
        });
    }

    /**
     * 保证预览方向正确
     *
     * @param appCompatActivity Activity
     * @param cameraId          相机Id
     * @param camera            相机
     */
    private void setCameraDisplayOrientation(AppCompatActivity appCompatActivity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        //rotation是预览Window的旋转方向，对于手机而言，当在清单文件设置Activity的screenOrientation="portait"时，
        //rotation=0，这时候没有旋转，当screenOrientation="landScape"时，rotation=1。
        int rotation = appCompatActivity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        //计算图像所要旋转的角度
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        int orientation = result;
        //调整预览图像旋转角度
        camera.setDisplayOrientation(result);

    }

    private void face(byte[] bytes, Camera camera) {
        List<FaceRectView.DrawInfo> irDrawInfoList = new ArrayList<>();
        int glSurfaceRectStrokeWidth = 2;

        Camera.Size size = camera.getParameters().getPreviewSize();
        List<FaceInfo> faceInfoList = new ArrayList<>();
        int code = Constants.faceEngine.detectFaces(bytes, size.width, size.height,
                FaceEngine.CP_PAF_NV21, faceInfoList);
        if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
            Log.i(TAG, "detectFaces, face num is : " + faceInfoList.size());
            if (faceInfoList.size() > 1) {
                ToastUtils.show("不支持多张人脸扫描");
                return;
            } else {
//                GameView gameView = new GameView(this, faceInfoList.get(0).getRect());
//                if (rl_camera.getChildCount() > 1) {
//                    rl_camera.removeViewAt(1);
//                }
//                if (rl_camera != null) {
//                    rl_camera.addView(gameView);
//                }

                Rect originalRect = faceInfoList.get(0).getRect();
                Log.i(TAG, "Rect: left" + originalRect.left + "top"
                        + originalRect.top + "right" + originalRect.right + "top" + originalRect.bottom);
                // 将Rect绘制到NV21数据上并渲染
                mSurfaceView.renderNV21WithFaceRect(bytes, originalRect, glSurfaceRectStrokeWidth);
                // 使用FaceRectView绘制已适配的人脸框
                irDrawInfoList.add(new FaceRectView.DrawInfo(originalRect, GenderInfo.UNKNOWN,
                        AgeInfo.UNKNOWN_AGE, LivenessInfo.UNKNOWN, Color.YELLOW, ""));
                faceView.drawRealtimeFaceInfo(irDrawInfoList);
            }
        } else {
            Log.i(TAG, "no face detected, code is : " + code);
        }
    }

    /**
     * 初始化
     */
    private void faceInit() {
        int scale = 32;
        int maxFaceNum = 5;
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

}
