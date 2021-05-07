package com.chenyang.androidproject.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.bean.FacePreviewInfo;
import com.chenyang.androidproject.databinding.ActivityLivenessDetectBinding;
import com.chenyang.androidproject.utils.CameraListener;
import com.chenyang.androidproject.utils.ConfigUtil;
import com.chenyang.androidproject.utils.DualCameraHelper;
import com.chenyang.androidproject.utils.FaceRectTransformer;
import com.chenyang.androidproject.utils.LivenessDetectViewModel;
import com.chenyang.androidproject.utils.LivenessType;
import com.chenyang.androidproject.utils.PreviewConfig;
import com.chenyang.androidproject.view.FaceRectView;
import com.hjq.toast.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LivenessDetectActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "LivenessDetectActivity";
    private DualCameraHelper rgbCameraHelper;
    private FaceRectTransformer rgbFaceRectTransformer;
    PreviewConfig previewConfig;
    private ActivityLivenessDetectBinding binding;
    private LivenessDetectViewModel livenessDetectViewModel;
    private int circleLeft;
    private int circleTop;
    private int circleRight;
    private int circleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_liveness_detect);

        //保持亮屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }

        // Activity启动后就锁定为启动时的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        initModel();
        initView();
        initViewModel();
    }


    private void initModel() {
        boolean switchCamera = ConfigUtil.isSwitchCamera(this);
        previewConfig = new PreviewConfig(
                switchCamera ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK,
                switchCamera ? Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT,
                Integer.parseInt(ConfigUtil.getRgbCameraAdditionalRotation(this)),
                Integer.parseInt(ConfigUtil.getIrCameraAdditionalRotation(this))
        );
    }

    private void initViewModel() {
        livenessDetectViewModel = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        )
                .get(LivenessDetectViewModel.class);
        livenessDetectViewModel.init(DualCameraHelper.canOpenDualCamera());
    }

    private void initView() {
        //在布局结束后才做初始化操作
        binding.dualCameraTexturePreviewRgb.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {

        if (rgbCameraHelper != null) {
            rgbCameraHelper.release();
            rgbCameraHelper = null;
        }

        livenessDetectViewModel.destroy();
        super.onDestroy();
    }

    /**
     * 调整View的宽高，使2个预览同时显示
     *
     * @param previewView        显示预览数据的view
     * @param faceRectView       画框的view
     * @param previewSize        预览大小
     * @param displayOrientation 相机旋转角度
     * @return 调整后的LayoutParams
     */
    private ViewGroup.LayoutParams adjustPreviewViewSize(View previewView, FaceRectView faceRectView, Camera.Size previewSize, int displayOrientation, float scale) {
        ViewGroup.LayoutParams layoutParams = previewView.getLayoutParams();
        int measuredWidth = previewView.getMeasuredWidth();
        int measuredHeight = previewView.getMeasuredHeight();
        float ratio = ((float) previewSize.height) / (float) previewSize.width;
        if (ratio > 1) {
            ratio = 1 / ratio;
        }
        if (displayOrientation % 180 == 0) {
            layoutParams.width = measuredWidth;
            layoutParams.height = (int) (measuredWidth * ratio);
        } else {
            layoutParams.height = measuredHeight;
            layoutParams.width = (int) (measuredHeight * ratio);
        }
        layoutParams.width *= scale;
        layoutParams.height *= scale;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (layoutParams.width >= metrics.widthPixels) {
            float viewRatio = layoutParams.width / ((float) metrics.widthPixels);
            layoutParams.width /= viewRatio;
            layoutParams.height /= viewRatio;
        }
        if (layoutParams.height >= metrics.heightPixels) {
            float viewRatio = layoutParams.height / ((float) metrics.heightPixels);
            layoutParams.width /= viewRatio;
            layoutParams.height /= viewRatio;
        }

        previewView.setLayoutParams(layoutParams);
        faceRectView.setLayoutParams(layoutParams);
        return layoutParams;
    }

    private void initRgbCamera() {
        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Camera.Size previewSizeRgb = camera.getParameters().getPreviewSize();
                ViewGroup.LayoutParams layoutParams = adjustPreviewViewSize(
                        binding.dualCameraTexturePreviewRgb, binding.dualCameraFaceRectView,
                        previewSizeRgb, displayOrientation, 1.0f);
                rgbFaceRectTransformer = new FaceRectTransformer(
                        previewSizeRgb.width, previewSizeRgb.height,
                        layoutParams.width, layoutParams.height,
                        displayOrientation, cameraId, isMirror,
                        ConfigUtil.isDrawRgbRectHorizontalMirror(getApplicationContext()),
                        ConfigUtil.isDrawRgbRectVerticalMirror(getApplicationContext())
                );
                livenessDetectViewModel.onRgbCameraOpened(camera);
                livenessDetectViewModel.setRgbFaceRectTransformer(rgbFaceRectTransformer);
            }

            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                binding.dualCameraFaceRectView.clearFaceInfo();
                List<FacePreviewInfo> facePreviewInfoList = livenessDetectViewModel.onPreviewFrame(nv21);
                List<FacePreviewInfo> facePreviewInfoListNew = new ArrayList<FacePreviewInfo>();
                if (facePreviewInfoList != null && rgbFaceRectTransformer != null) {
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        if ((facePreviewInfoList.get(i).getRgbTransformedRect().left >= circleLeft) && (facePreviewInfoList.get(i).getRgbTransformedRect().top >= circleTop) && (facePreviewInfoList.get(i).getRgbTransformedRect().right <= circleRight) && (facePreviewInfoList.get(i).getRgbTransformedRect().bottom <= circleBottom)) {
                            facePreviewInfoListNew.add(facePreviewInfoList.get(i));
                        }
                    }
                    Log.i(TAG, "facePreviewInfoList:" + facePreviewInfoList);
                    Log.i(TAG, "facePreviewInfoListNew:" + facePreviewInfoListNew);
                    Log.i(TAG, "人脸数:" + facePreviewInfoListNew.size());
                    if (facePreviewInfoListNew.size() > 1) {
                        ToastUtils.show("不支持多张人脸");
                        return;
                    }
                    if (facePreviewInfoListNew.size() == 1 && facePreviewInfoListNew.get(0).getRgbLiveness() == 1) {
                        drawPreviewInfo(facePreviewInfoListNew);

//                        new Thread() {
//                            @Override
//                            public void run() {
//                                super.run();
//                                getPicture(nv21, camera);
//                            }
//                        }.start();
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (rgbFaceRectTransformer != null) {
                    rgbFaceRectTransformer.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };

        rgbCameraHelper = new DualCameraHelper.Builder()
                .previewViewSize(new Point(binding.dualCameraTexturePreviewRgb.getMeasuredWidth(), binding.dualCameraTexturePreviewRgb.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(ConfigUtil.isDrawRgbPreviewHorizontalMirror(this))
                .additionalRotation(Integer.parseInt(ConfigUtil.getRgbCameraAdditionalRotation(this)))
                .previewSize(livenessDetectViewModel.loadPreviewSize())
                .previewOn(binding.dualCameraTexturePreviewRgb)
                .cameraListener(cameraListener)
                .build();
        rgbCameraHelper.init();
        rgbCameraHelper.start();
    }

    /**
     * 绘制RGB、IR画面的实时人脸信息
     *
     * @param facePreviewInfoList RGB画面的实时人脸信息
     */
    private void drawPreviewInfo(List<FacePreviewInfo> facePreviewInfoList) {
        if (rgbFaceRectTransformer != null) {
            List<FaceRectView.DrawInfo> rgbDrawInfoList = livenessDetectViewModel.getDrawInfo(facePreviewInfoList, LivenessType.RGB);
            binding.dualCameraFaceRectView.drawRealtimeFaceInfo(rgbDrawInfoList);
        }
    }

    @Override
    public void onGlobalLayout() {
        binding.dualCameraTexturePreviewRgb.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        circleLeft = binding.ivCircle.getLeft();
        circleTop = binding.ivCircle.getTop();
        circleRight = binding.ivCircle.getRight();
        circleBottom = binding.ivCircle.getBottom();
        Log.i("圆形图片的位置:", "left:" + circleLeft + "top:" + circleTop + "right:" + circleRight + "bottom:" + circleBottom);

        initRgbCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeCamera();
    }

    private void resumeCamera() {
        if (rgbCameraHelper != null) {
            rgbCameraHelper.start();
        }
    }

    @Override
    protected void onPause() {
        pauseCamera();
        super.onPause();
    }

    private void pauseCamera() {
        if (rgbCameraHelper != null) {
            rgbCameraHelper.stop();
        }
    }

    private void getPicture(byte[] bytes, Camera camera) {
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, stream);
        Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(-90);
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        Log.d("检测到人脸信息(bmp 2)：", "bmp2:" + bmp);
        Bitmap bitmap = bmp.copy(Bitmap.Config.RGB_565, true);
        pauseCamera();
    }

    public void pausePreview(View view) {
        pauseCamera();
    }

    public void resumePreview(View view) {
        resumeCamera();
    }
}
