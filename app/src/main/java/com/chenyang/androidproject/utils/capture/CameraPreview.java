package com.chenyang.androidproject.utils.capture;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.hjq.toast.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "CameraPreview";
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Activity mContext;
    private CameraListener listener;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    //    private int cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private int displayDegree = 0;
    private long lastModirTime;
    private OnPreviewFrameListener mOnPreviewFrameListener;
    private Handler mBackgroundHandler;
    public boolean hasCamera = false;
    private OnFaceIdentifyListener mOnFaceIdentifyListener;

    public CameraPreview(Activity context, OnPreviewFrameListener listener) {
        super(context);
        mContext = context;
        releaseCamera();
        try {
            //判断是否支持蓝牙模块
            if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                //获取相机数量
                int numberOfCameras = Camera.getNumberOfCameras();
                if (numberOfCameras > 0) {
                    for (int i = 0; i < numberOfCameras; i++) {
                        Camera.CameraInfo info = new Camera.CameraInfo();
                        Camera.getCameraInfo(i, info);
                        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                            break;
                        }
//                        else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
//                            break;
//                        }
                    }
                    mCamera = Camera.open(cameraId);
                    mHolder = getHolder();
                    mHolder.addCallback(this);
                    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                    hasCamera = true;
                } else {
                    hasCamera = false;
                    Log.d("CameraPreview", "没有检测到摄像头");
                }
            } else {
                hasCamera = false;
                Log.d("CameraPreview", "没有摄像头模块");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("打开摄像头失败", ">>>" + e.getMessage());
        }
        this.mOnPreviewFrameListener = listener;
    }


    /**
     * 拍照获取bitmap
     */
    public void captureImage() {

        try {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (null != listener) {
                        Bitmap bitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), -90);
                        listener.onCaptured(bitmap);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != listener) {
                listener.onCaptured(null);
            }
        }
    }

    /**
     * 预览拍照
     */
    public void startPreview() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } else {
            Log.d("startPreview", "未找到摄像头");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != mCamera) {
            mCamera.autoFocus(null);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            startCamera(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            startCamera(mHolder);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void startCamera(SurfaceHolder holder) throws IOException {
        mCamera.setPreviewDisplay(holder);
        setCameraDisplayOrientation(mContext, cameraId, mCamera);
        Camera.Size preSize = getCameraSize();
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size previewSize = parameters.getPreviewSize();
        Log.d("PreviewSize", "getPreviewSize height:" + previewSize.height + ",width:" + previewSize.width);
        Log.d("PreviewSize", "setPreviewSize height:" + preSize.height + ",width" + preSize.width);
        parameters.setPreviewSize(preSize.width, preSize.height);
        parameters.setPictureSize(preSize.width, preSize.height);
        parameters.setJpegQuality(100);
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    public Camera.Size getCameraSize() {
        if (null != mCamera) {
            Camera.Parameters parameters = mCamera.getParameters();
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            Camera.Size preSize = CameraUtil.getCloselyPreSize(true, metrics.widthPixels, metrics.heightPixels,
                    parameters.getSupportedPreviewSizes());
            return preSize;
        }
        return null;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * Android API: Display Orientation Setting
     * Just change screen display orientation,
     * the rawFrame data never be changed.
     */
    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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
        Log.d("rotation", "rotation>>>" + degrees);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayDegree = (info.orientation + degrees) % 360;
            displayDegree = (360 - displayDegree) % 360;  // compensate the mirror
        } else {
            displayDegree = (info.orientation - degrees + 360) % 360;
//            int camera_direction_position = (int) SPUtils.get(getContext(), SPUtils.CAMERA_DIRECTION, 0);
//            if (camera_direction_position == 0) {
//                //向上
//                displayDegree = 270;
//            } else if (camera_direction_position == 1) {
//                //向右
//                displayDegree = 0;
//            } else if (camera_direction_position == 2) {
//                //向下
//                displayDegree = 90;
//            } else if (camera_direction_position == 3) {
//                //向左
//                displayDegree = 180;
//            }
        }
        Log.d("rotation", "displayDegree>>>" + displayDegree);
        camera.setDisplayOrientation(displayDegree);
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 释放资源
     */
    public synchronized void releaseCamera() {
        try {
            if (null != mCamera) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();//停止预览
                mCamera.release(); // 释放相机资源
                mCamera = null;
                Log.d("releaseCamera", "释放相机");
            }
            if (null != mHolder) {
                mHolder.removeCallback(this);
                mHolder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
//        Log.d(TAG, "onPreviewFrame:" + camera);
        long l = System.currentTimeMillis() - lastModirTime;
        if (l <= 1000 || bytes == null || bytes.length == 0) {
            return;
        }
//        face(bytes, camera);
        faceNew(bytes, camera);
        // new FaceThread(bytes, camera).start();
        lastModirTime = System.currentTimeMillis();
    }

    private void faceNew(byte[] bytes, Camera camera) {
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
                mOnFaceIdentifyListener.onSuccess(faceInfoList.get(0).getRect());
                Log.i(TAG, "bottom：" + faceInfoList.get(0).getRect().bottom + "\n"
                        + "left：" + faceInfoList.get(0).getRect().left + "\n"
                        + "top：" + faceInfoList.get(0).getRect().top + "\n"
                        + "right：" + faceInfoList.get(0).getRect().right + "\n");
                List<Float> imageQualityList = new ArrayList<>();
                // 对人脸信息进行图像质量检测
//                int imageQualityDetectCode = Constants.faceEngine.imageQualityDetect(bytes, size.width, size.height,
//                        FaceEngine.CP_PAF_NV21, faceInfoList, imageQualityList);
//                if (imageQualityDetectCode == ErrorInfo.MOK) {
//                    for (int i = 0; i < imageQualityList.size(); i++) {
//                        Log.i(TAG, "imageQualityDetect success: imageQuality Of face[" + i + "] is :" + imageQualityList.get(i));
//                    }
//                } else {
//                    Log.e(TAG, "imageQualityDetect code: " + imageQualityDetectCode);
//                }

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
                if (mOnPreviewFrameListener != null) {
                    mOnPreviewFrameListener.onPreviewFrame(bitmap);
                }
            }
        } else {
            Log.i(TAG, "no face detected, code is : " + code);
        }
    }

    private void face(byte[] bytes, Camera camera) {
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
        Log.d("检测到人脸信息：", "face--生成人脸照片1" + image);
        if (image != null) {
            Log.d("检测到人脸信息：", "face--生成人脸照片2");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Log.d("检测到人脸信息(stream)：", "stream:" + stream);
            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 70, stream);
            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            Log.d("检测到人脸信息(bmp)：", "bmp:" + bmp);
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(-90);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            Log.d("检测到人脸信息(bmp 2)：", "bmp2:" + bmp);
            Bitmap bitmap = bmp.copy(Bitmap.Config.RGB_565, true);
            Log.d("检测到人脸信息(bitmap)：", "bitmap:" + bitmap);
            Bitmap bitmapTailoring = cropBitmap(bitmap);
            FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 2);
            Log.d("检测到人脸信息(faceDetector)：", "faceDetector:" + faceDetector);
            FaceDetector.Face[] face = new FaceDetector.Face[2];
            Log.d("检测到人脸信息(face)：", "face:" + face);
            int faces = faceDetector.findFaces(bitmap, face);
            Log.d("检测到人脸信息(faces)：", "faces:" + faces);
            FaceDetector.Face face1 = face[0];
            FaceDetector.Face face2 = face[1];
//            if(face1!=null){
//                bitmap2uri(getContext(),bitmap);
//            }
            Log.d("检测到人脸信息：", "face--生成人脸照片3--face1:" + face1);
            Log.d("检测到人脸信息：", "face--生成人脸照片3--face2:" + face2);

            if (face1 != null && face2 != null) {
                ToastUtils.show("不支持多张人脸扫描");
                return;
            }
            if (face1 != null && face2 == null) {
                if (face1 != null) {
                    if (face2 != null) {
                        ToastUtils.show("不支持多张人脸扫描");
                        return;
                    }
                    // 获取双眼的中心点，用一个PointF来接收其x、y坐标
                    PointF point = new PointF();
                    face1.getMidPoint(point);
                    float fwidth = bmp.getWidth();
                    float fheight = bitmap.getHeight();
                    float fx = point.x;
                    float fy = point.y;

                    Log.d("检测到人脸信息：", "face--生成人脸照片" + point.y + "---" + point.x + "getHeight()--" + fheight + "getWidth()" + fwidth);
                    // 获取该部位为人脸的可信度，0~1
                    float confidence = face1.confidence();
                    // 获取双眼间距
                    float eyesDistance = face1.eyesDistance();
                    if (confidence > 0.3) {
                        if (((fx >= fwidth * 0.2f) && (fx <= fwidth * 0.8f)) && ((fy >= fheight * 0.2f) && (fy <= fheight * 0.8f))) {
                            Log.d(TAG, "onPreviewFrame检测到人脸成功");
                            if (mOnPreviewFrameListener != null) {
                                mOnPreviewFrameListener.onPreviewFrame(bitmap);
                            }
                        } else {
                            Log.d(TAG, "onPreviewFrame检测到人脸,不在范围内");
                        }
//                    Bitmap bitmapTailoring = cropBitmap(bitmap);//对得到的原图进行裁切

                    }
                    Log.d("检测到人脸信息：", "人脸数" + faces + "人脸可信度" + confidence + "人脸双眼的间距" + eyesDistance);
                }
            } else if (face1 == null && face2 != null) {
                if (face1 != null) {
                    if (face2 != null) {
                        ToastUtils.show("不支持多张人脸扫描");
                        return;
                    }
                    // 获取双眼的中心点，用一个PointF来接收其x、y坐标
                    PointF point = new PointF();
                    face2.getMidPoint(point);
                    float fwidth = bmp.getWidth();
                    float fheight = bitmap.getHeight();
                    float fx = point.x;
                    float fy = point.y;

                    Log.d("检测到人脸信息：", "face--生成人脸照片" + point.y + "---" + point.x + "getHeight()--" + fheight + "getWidth()" + fwidth);
                    // 获取该部位为人脸的可信度，0~1
                    float confidence = face2.confidence();
                    // 获取双眼间距
                    float eyesDistance = face2.eyesDistance();
                    if (confidence > 0.3) {
                        if (((fx >= fwidth * 0.2f) && (fx <= fwidth * 0.8f)) && ((fy >= fheight * 0.2f) && (fy <= fheight * 0.8f))) {
                            Log.d(TAG, "onPreviewFrame检测到人脸成功");
                            if (mOnPreviewFrameListener != null) {
                                mOnPreviewFrameListener.onPreviewFrame(bitmap);
                            }
                        } else {
                            Log.d(TAG, "onPreviewFrame检测到人脸,不在范围内");
                        }
//                    Bitmap bitmapTailoring = cropBitmap(bitmap);//对得到的原图进行裁切

                    }
                    Log.d("检测到人脸信息：", "人脸数" + faces + "人脸可信度" + confidence + "人脸双眼的间距" + eyesDistance);
                }
            }

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    private Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        Log.d("cropBitmap:", "w:" + w + ",h:" + h);
        return Bitmap.createBitmap(bitmap, w / 6, h / 4, w * 2 / 3, h * 2 / 3);
    }

    public interface OnPreviewFrameListener {
        void onPreviewFrame(Bitmap bitmap);
    }

    private boolean bitmap2uri(Context c, Bitmap b) {//c.getCacheDir()
        //   /Android/data/你的报名/cache/1600739295328.jpg
        File path = new File(c.getExternalCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        try {
            OutputStream os = new FileOutputStream(path);
            b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public interface OnFaceIdentifyListener {
        void onSuccess(Rect rect);
    }

    public void setOnFaceIdentifyListener(OnFaceIdentifyListener onFaceIdentifyListener) {
        mOnFaceIdentifyListener = onFaceIdentifyListener;
    }

}
