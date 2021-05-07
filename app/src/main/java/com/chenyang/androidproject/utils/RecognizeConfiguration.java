package com.chenyang.androidproject.utils;

import com.arcsoft.face.LivenessParam;

/**
 * 识别相关的配置项
 */
public class RecognizeConfiguration {
    /**
     * 产生特征提取失败示语的特征提取次数（小于该值不提示）
     */
    private int extractRetryCount;
    /**
     * 产生活体检测失败示语的活体检测次数（小于该值不提示）
     */
    private int livenessRetryCount;
    /**
     * 最大人脸检测数量
     */
    private int maxDetectFaces;
    /**
     * 识别阈值
     */
    private float similarThreshold;
    /**
     * 图像质量检测阈值
     */
    private float imageQualityThreshold;
    /**
     * 识别失败重试间隔
     */
    private int recognizeFailedRetryInterval;
    /**
     * 活体检测未通过重试间隔
     */
    private int livenessFailedRetryInterval;
    /**
     * 启用活体
     */
    private boolean enableLiveness;
    /**
     * 启用图像质量检测
     */
    private boolean enableImageQuality;
    /**
     * 仅识别最大人脸
     */
    private boolean keepMaxFace;
    /**
     * 活体阈值设置
     */
    private LivenessParam livenessParam;

    public RecognizeConfiguration(Builder builder) {
        this.extractRetryCount = builder.extractRetryCount;
        this.livenessRetryCount = builder.livenessRetryCount;
        this.livenessFailedRetryInterval = builder.livenessFailedRetryInterval;
        this.maxDetectFaces = builder.maxDetectFaces;
        this.similarThreshold = builder.similarThreshold;
        this.imageQualityThreshold = builder.imageQualityThreshold;
        this.enableLiveness = builder.enableLiveness;
        this.enableImageQuality = builder.enableImageQuality;
        this.keepMaxFace = builder.keepMaxFace;
        this.recognizeFailedRetryInterval = builder.recognizeFailedRetryInterval;
        this.livenessParam = builder.livenessParam;
    }

    //TODO： demo不实现所有配置，若以下项也需要进行自定义配置，可参考其他配置项实现
    public static class Builder {
        private int extractRetryCount = 3;
        private int livenessRetryCount = 3;
        private int maxDetectFaces = 3;
        private int recognizeFailedRetryInterval = 1000;
        private int livenessFailedRetryInterval = 1000;
        private float similarThreshold = 0.8f;
        private float imageQualityThreshold = 0.35f;
        private boolean enableLiveness = false;
        private boolean enableImageQuality = false;
        private boolean keepMaxFace = false;
        private LivenessParam livenessParam;

        public Builder recognizeFailedRetryInterval(int val) {
            this.recognizeFailedRetryInterval = val;
            return this;
        }

        public Builder livenessFailedRetryInterval(int val) {
            this.livenessFailedRetryInterval = val;
            return this;
        }

        public Builder extractRetryCount(int val) {
            this.extractRetryCount = val;
            return this;
        }

        public Builder livenessRetryCount(int val) {
            this.livenessRetryCount = val;
            return this;
        }

        public Builder maxDetectFaces(int val) {
            this.maxDetectFaces = val;
            return this;
        }

        public Builder similarThreshold(float val) {
            this.similarThreshold = val;
            return this;
        }

        public Builder imageQualityThreshold(float val) {
            this.imageQualityThreshold = val;
            return this;
        }

        public Builder enableLiveness(boolean val) {
            this.enableLiveness = val;
            return this;
        }


        public Builder enableImageQuality(boolean val) {
            this.enableImageQuality = val;
            return this;
        }

        public Builder keepMaxFace(boolean val) {
            this.keepMaxFace = val;
            return this;
        }

        public Builder livenessParam(LivenessParam val) {
            this.livenessParam = val;
            return this;
        }


        public RecognizeConfiguration build() {
            return new RecognizeConfiguration(this);
        }
    }

    public float getImageQualityThreshold() {
        return imageQualityThreshold;
    }

    public boolean isEnableImageQuality() {
        return enableImageQuality;
    }

    public LivenessParam getLivenessParam() {
        return livenessParam;
    }

    public int getExtractRetryCount() {
        return extractRetryCount;
    }

    public int getLivenessRetryCount() {
        return livenessRetryCount;
    }

    public int getMaxDetectFaces() {
        return maxDetectFaces;
    }

    public float getSimilarThreshold() {
        return similarThreshold;
    }

    public boolean isEnableLiveness() {
        return enableLiveness;
    }


    public int getRecognizeFailedRetryInterval() {
        return recognizeFailedRetryInterval;
    }

    public int getLivenessFailedRetryInterval() {
        return livenessFailedRetryInterval;
    }

    public boolean isKeepMaxFace() {
        return keepMaxFace;
    }

    public void setKeepMaxFace(boolean keepMaxFace) {
        this.keepMaxFace = keepMaxFace;
    }

    @Override
    public String toString() {
        return
                        "extractRetryCount: " + extractRetryCount + "\r\n" +
                        "similarThreshold: " + similarThreshold + "\r\n" +
                        "recognizeFailedRetryInterval: " + recognizeFailedRetryInterval + "\r\n" +

                        "keepMaxFace: " + keepMaxFace + "\r\n" +
                        "maxDetectFaces: " + maxDetectFaces + "\r\n" +

                        "enableImageQuality: " + enableImageQuality + "\r\n" +
                        "imageQualityThreshold: " + imageQualityThreshold + "\r\n" +

                        "enableLiveness: " + enableLiveness + "\r\n" +
                        "livenessRetryCount: " + livenessRetryCount + "\r\n" +
                        "livenessParams: " + (livenessParam == null ? null : (livenessParam.getRgbThreshold() + "," + livenessParam.getIrThreshold()));

    }
}
