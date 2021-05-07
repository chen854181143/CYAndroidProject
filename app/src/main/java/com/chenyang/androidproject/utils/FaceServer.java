package com.chenyang.androidproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.imageutil.ArcSoftImageFormat;
import com.arcsoft.imageutil.ArcSoftImageUtil;
import com.arcsoft.imageutil.ArcSoftImageUtilError;
import com.arcsoft.imageutil.ArcSoftRotateDegree;
import com.chenyang.androidproject.common.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 人脸库操作类，包含注册和搜索
 */
public class FaceServer {
    private static final String TAG = "FaceServer";
    private static FaceEngine faceEngine = null;
    private static volatile FaceServer faceServer = null;
//    private static List<FaceEntity> faceRegisterInfoList;
    private String imageRootPath;
    /**
     * 最大注册人脸数
     */
    private static final int MAX_REGISTER_FACE_COUNT = 30000;
    /**
     * 是否正在搜索人脸，保证搜索操作单线程进行
     */
    private final Object searchLock = new Object();

    public static FaceServer getInstance() {
        if (faceServer == null) {
            synchronized (FaceServer.class) {
                if (faceServer == null) {
                    faceServer = new FaceServer();
                }
            }
        }
        return faceServer;
    }

    public interface OnInitFinishedCallback {
        void onFinished(int faceCount);
    }

    public void init(Context context) {
        init(context, null);
    }

    public synchronized void init(Context context, OnInitFinishedCallback onInitFinishedCallback) {
        if (faceEngine == null && context != null) {
            faceEngine = new FaceEngine();
            int engineCode = faceEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_ALL_OUT, 16, 1, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT);
            if (engineCode == ErrorInfo.MOK) {
                initFaceList(context, onInitFinishedCallback);
            } else {
                faceEngine = null;
                Log.e(TAG, "init: failed! code = " + engineCode);
            }
        }
//        if (faceRegisterInfoList != null && onInitFinishedCallback != null) {
//            onInitFinishedCallback.onFinished(faceRegisterInfoList.size());
//        }
    }

    /**
     * 销毁
     */
    public synchronized void release() {
//        if (faceRegisterInfoList != null) {
//            faceRegisterInfoList.clear();
//            faceRegisterInfoList = null;
//        }
        if (faceEngine != null) {
            synchronized (faceEngine) {
                faceEngine.unInit();
            }
            faceEngine = null;
        }
        faceServer = null;
    }

    /**
     * 初始化人脸特征数据以及人脸特征数据对应的注册图
     *
     * @param context                上下文对象
     * @param onInitFinishedCallback 加载完成的回调
     */
    private void initFaceList(final Context context, final OnInitFinishedCallback onInitFinishedCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (FaceServer.this) {
//                    if (faceRegisterInfoList != null) {
//                        return;
//                    }
//                    faceRegisterInfoList = FaceDatabase.getInstance(context).faceDao().getAllFaces();
//                    if (onInitFinishedCallback != null) {
//                        onInitFinishedCallback.onFinished(faceRegisterInfoList.size());
//                    }
                }
            }
        }).start();
    }

    /**
     * 获取存放注册照的文件夹路径
     *
     * @return 存放注册照的文件夹路径
     */
    private String getImageDir() {
        return MyApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + File.separator + "faceDB" + File.separator + "registerFaces";
    }

    /**
     * 根据用户名获取注册图保存路径
     *
     * @param name 用户名
     * @return 图片保存地址
     */
    private String getImagePath(String name) {
        if (imageRootPath == null) {
            imageRootPath = getImageDir();
            File dir = new File(imageRootPath);
            if (!dir.exists() && !dir.mkdirs()) {
                return null;
            }
        }
        return imageRootPath + File.separator + name + "_" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 在特征库中搜索
     *
     * @param faceFeature 传入特征数据
     * @return 比对结果
     */
    public CompareResult getTopOfFaceLib(FaceFeature faceFeature) {
//        if (faceEngine == null || faceFeature == null || faceRegisterInfoList == null || faceRegisterInfoList.size() == 0) {
//            return null;
//        }
//        long start = System.currentTimeMillis();
//        FaceFeature tempFaceFeature = new FaceFeature();
//        FaceSimilar faceSimilar = new FaceSimilar();
//        float maxSimilar = 0;
//        int maxSimilarIndex = -1;
//
//        int code = ErrorInfo.MOK;
//
//        synchronized (searchLock) {
//            for (int i = 0; i < faceRegisterInfoList.size(); i++) {
//                tempFaceFeature.setFeatureData(faceRegisterInfoList.get(i).getFeatureData());
//                code = faceEngine.compareFaceFeature(faceFeature, tempFaceFeature, faceSimilar);
//                if (faceSimilar.getScore() > maxSimilar) {
//                    maxSimilar = faceSimilar.getScore();
//                    maxSimilarIndex = i;
//                }
//            }
//        }
//        if (maxSimilarIndex != -1) {
//            return new CompareResult(faceRegisterInfoList.get(maxSimilarIndex), maxSimilar, code, System.currentTimeMillis() - start);
//        }
        return null;
    }

    /**
     * 将图像中需要截取的Rect向外扩张一倍，若扩张一倍会溢出，则扩张到边界，若Rect已溢出，则收缩到边界
     *
     * @param width   图像宽度
     * @param height  图像高度
     * @param srcRect 原Rect
     * @return 调整后的Rect
     */
    private static Rect getBestRect(int width, int height, Rect srcRect) {
        if (srcRect == null) {
            return null;
        }
        Rect rect = new Rect(srcRect);

        // 原rect边界已溢出宽高的情况
        int maxOverFlow = Math.max(-rect.left, Math.max(-rect.top, Math.max(rect.right - width, rect.bottom - height)));
        if (maxOverFlow >= 0) {
            rect.inset(maxOverFlow, maxOverFlow);
            return rect;
        }

        // 原rect边界未溢出宽高的情况
        int padding = rect.height() / 2;

        // 若以此padding扩张rect会溢出，取最大padding为四个边距的最小值
        if (!(rect.left - padding > 0 && rect.right + padding < width && rect.top - padding > 0 && rect.bottom + padding < height)) {
            padding = Math.min(Math.min(Math.min(rect.left, width - rect.right), height - rect.bottom), rect.top);
        }
        rect.inset(-padding, -padding);
        return rect;
    }
}
