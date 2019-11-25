package com.chenyang.androidproject.utils;


import com.chenyang.androidproject.common.MyApplication;

/**
 * dp px 转换工具类
 */
public class DensityUtil {

	private static float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;

	/**
	 * dp 转 px
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {

		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px 转 dp
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {

		return (int) (pxValue / scale + 0.5f);
	}

}
