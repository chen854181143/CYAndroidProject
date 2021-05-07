package com.chenyang.androidproject.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.LivenessParam;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.chenyang.androidproject.bean.FacePreviewInfo;
import com.chenyang.androidproject.common.MyApplication;
import com.chenyang.androidproject.view.FaceRectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LivenessDetectViewModel extends ViewModel {
    private static final String TAG = "LivenessDetectViewModel";

    private FaceEngine flEngine;
    private FaceEngine ftEngine;

    private FaceHelper faceHelper;
    private byte[] irNv21;
    private Camera.Size previewSize;

    // 各个引擎初始化的错误码
    private MutableLiveData<Integer> ftInitCode = new MutableLiveData<>();
    private MutableLiveData<Integer> flInitCode = new MutableLiveData<>();

    private ExecutorService livenessExecutor;

    private ConcurrentHashMap<Integer, Integer> rgbLivenessMap;
    private ConcurrentHashMap<Integer, Integer> irLivenessMap;
    private final ReentrantLock livenessDetectLock = new ReentrantLock();

    int livenessMask = FaceEngine.ASF_LIVENESS;

    public void init(boolean canOpenDualCamera) {
        Context context = MyApplication.getContext();
        rgbLivenessMap = new ConcurrentHashMap<>();
        if (canOpenDualCamera) {
            irLivenessMap = new ConcurrentHashMap<>();
            livenessMask |= FaceEngine.ASF_IR_LIVENESS;
        }

        LivenessParam livenessParam = new LivenessParam(ConfigUtil.getRgbLivenessThreshold(context), ConfigUtil.getIrLivenessThreshold(context));

        ftEngine = new FaceEngine();
        ftInitCode.postValue(ftEngine.init(context, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(context),
                ConfigUtil.getRecognizeScale(context), 5, FaceEngine.ASF_FACE_DETECT));

        flEngine = new FaceEngine();
        flInitCode.postValue(flEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE,
                DetectFaceOrientPriority.ASF_OP_0_ONLY, 16, 5, livenessMask));
        flEngine.setLivenessParam(livenessParam);

        livenessExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("flThread-" + t.getId());
                return t;
            }
        });
    }


    /**
     * 销毁引擎，faceHelper中可能会有特征提取耗时操作仍在执行，加锁防止crash
     */
    private void unInit() {
        if (ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i(TAG, "unInitEngine: " + ftUnInitCode);
            }
        }
        if (flEngine != null) {
            synchronized (flEngine) {
                int frUnInitCode = flEngine.unInit();
                Log.i(TAG, "unInitEngine: " + frUnInitCode);
            }
        }
    }

    public void destroy() {
        if (livenessExecutor != null) {
            livenessExecutor.shutdown();
            livenessExecutor = null;
        }
        unInit();
    }

    public void onRgbCameraOpened(Camera camera) {
        Camera.Size lastPreviewSize = previewSize;
        previewSize = camera.getParameters().getPreviewSize();
        // 切换相机的时候可能会导致预览尺寸发生变化
        initFaceHelper(lastPreviewSize);
    }

    public void setRgbFaceRectTransformer(FaceRectTransformer rgbFaceRectTransformer) {
        faceHelper.setRgbFaceRectTransformer(rgbFaceRectTransformer);
    }

    public List<FacePreviewInfo> onPreviewFrame(byte[] nv21) {
        List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21, irNv21, false);
        clearLeftFace(facePreviewInfoList);
        return processLiveness(nv21, irNv21, facePreviewInfoList);
    }

    /**
     * 删除已经离开的人脸
     *
     * @param facePreviewInfoList 人脸和trackId列表
     */
    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        Enumeration<Integer> keys = rgbLivenessMap.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == key) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                rgbLivenessMap.remove(key);
                if (irLivenessMap != null) {
                    irLivenessMap.remove(key);
                }
            }
        }
    }

    private List<FacePreviewInfo> processLiveness(byte[] nv21, byte[] irNv21, List<FacePreviewInfo> previewInfoList) {
        if (previewInfoList == null || previewInfoList.size() == 0) {
            return null;
        }
        if (!livenessDetectLock.isLocked() && livenessExecutor != null) {
            livenessExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    List<FacePreviewInfo> facePreviewInfoList = new LinkedList<>(previewInfoList);
                    livenessDetectLock.lock();
                    try {
                        int processRgbLivenessCode;
                        synchronized (flEngine) {
                            processRgbLivenessCode = flEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, Arrays.asList(facePreviewInfoList.get(0).getFaceInfoRgb()), FaceEngine.ASF_LIVENESS);
                        }
                        if (processRgbLivenessCode != ErrorInfo.MOK) {
                            Log.e(TAG, "process RGB Liveness error: " + processRgbLivenessCode);
                        } else {
                            List<LivenessInfo> rgbLivenessInfoList = new ArrayList<>();
                            int getRgbLivenessCode = flEngine.getLiveness(rgbLivenessInfoList);
                            if (getRgbLivenessCode != ErrorInfo.MOK) {
                                Log.e(TAG, "get RGB LivenessResult error: " + getRgbLivenessCode);
                            } else {
                                rgbLivenessMap.put(facePreviewInfoList.get(0).getTrackId(), rgbLivenessInfoList.get(0).getLiveness());
                            }
                        }
                        if ((livenessMask & FaceEngine.ASF_IR_LIVENESS) != 0) {
                            int processIrLivenessCode;
                            synchronized (flEngine) {
                                processIrLivenessCode = flEngine.processIr(irNv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, Arrays.asList(facePreviewInfoList.get(0).getFaceInfoIr()), FaceEngine.ASF_IR_LIVENESS);
                            }
                            if (processIrLivenessCode != ErrorInfo.MOK) {
                                Log.e(TAG, "process IR Liveness error: " + processIrLivenessCode);
                            } else {
                                List<LivenessInfo> irLivenessInfoList = new ArrayList<>();
                                int getIrLivenessCode = flEngine.getIrLiveness(irLivenessInfoList);
                                if (getIrLivenessCode != ErrorInfo.MOK) {
                                    Log.e(TAG, "get IR LivenessResult error: " + getIrLivenessCode);
                                } else {
                                    irLivenessMap.put(facePreviewInfoList.get(0).getTrackId(), irLivenessInfoList.get(0).getLiveness());
                                }
                            }
                        }
                    } finally {
                        livenessDetectLock.unlock();
                    }
                }
            });
        }
        for (FacePreviewInfo facePreviewInfo : previewInfoList) {
            Integer rgbLiveness = rgbLivenessMap.get(facePreviewInfo.getTrackId());
            if (rgbLiveness != null) {
                facePreviewInfo.setRgbLiveness(rgbLiveness);
            }
            if (irLivenessMap != null) {
                Integer irLiveness = irLivenessMap.get(facePreviewInfo.getTrackId());
                if (irLiveness != null) {
                    facePreviewInfo.setIrLiveness(irLiveness);
                }
            }
        }
        return previewInfoList;
    }

    private void initFaceHelper(Camera.Size lastPreviewSize) {
        if (faceHelper == null ||
                lastPreviewSize == null ||
                lastPreviewSize.width != previewSize.width || lastPreviewSize.height != previewSize.height) {
            Integer trackedFaceCount = null;
            // 记录切换时的人脸序号
            if (faceHelper != null) {
                trackedFaceCount = faceHelper.getTrackedFaceCount();
                faceHelper.release();
            }
            Context context = MyApplication.getContext();

            faceHelper = new FaceHelper.Builder()
                    .ftEngine(ftEngine)
                    .previewSize(previewSize)
                    .recognizeConfiguration(new RecognizeConfiguration.Builder().keepMaxFace(true).build())
                    .trackedFaceCount(trackedFaceCount == null ? ConfigUtil.getTrackedFaceCount(context) : trackedFaceCount)
                    .build();
        }
    }

    /**
     * 根据预览信息生成绘制信息
     *
     * @param facePreviewInfoList 预览信息
     * @return 绘制信息
     */
    public List<FaceRectView.DrawInfo> getDrawInfo(List<FacePreviewInfo> facePreviewInfoList, LivenessType livenessType) {
        List<FaceRectView.DrawInfo> drawInfoList = new ArrayList<>();
        for (int i = 0; i < facePreviewInfoList.size(); i++) {
            int liveness = livenessType == LivenessType.RGB ? facePreviewInfoList.get(i).getRgbLiveness() : facePreviewInfoList.get(i).getIrLiveness();
            Rect rect = livenessType == LivenessType.RGB ?
                    facePreviewInfoList.get(i).getRgbTransformedRect() :
                    facePreviewInfoList.get(i).getIrTransformedRect();
            // 根据识别结果和活体结果设置颜色
            int color;
            String name;
            switch (liveness) {
                case LivenessInfo.ALIVE:
                    color = RecognizeColor.COLOR_SUCCESS;
                    name = "ALIVE";
                    break;
                case LivenessInfo.NOT_ALIVE:
                    color = RecognizeColor.COLOR_FAILED;
                    name = "NOT_ALIVE";
                    break;
                default:
                    color = RecognizeColor.COLOR_UNKNOWN;
                    name = "UNKNOWN";
                    break;
            }

            drawInfoList.add(new FaceRectView.DrawInfo(rect, GenderInfo.UNKNOWN,
                    AgeInfo.UNKNOWN_AGE, liveness, color, name));
        }
        return drawInfoList;
    }

    public Point loadPreviewSize() {
        String[] size = ConfigUtil.getPreviewSize(MyApplication.getContext()).split("x");
        return new Point(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
    }
}
