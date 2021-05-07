package com.chenyang.androidproject.utils;


public interface RecognizeCallback {
    /**
     * 识别结果回调
     *
     * @param compareResult 比对结果
     * @param liveness      活体值
     * @param similarPass   是否通过（依据设置的阈值）
     */
    void onRecognized(CompareResult compareResult, Integer liveness, boolean similarPass);
}
