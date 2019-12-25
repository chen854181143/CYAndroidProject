package com.chenyang.androidproject.view.evaluator;

import android.animation.TypeEvaluator;

public class MyEvalutor implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (200 + startInt + fraction * (endValue - startInt));
    }
}
