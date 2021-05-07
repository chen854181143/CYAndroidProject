package com.chenyang.androidproject.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author cloudborn
 */
public class GfpUtils {
    private static String TAG = GfpUtils.class.getSimpleName();
    private int fd = 0;
    private UsbManager mManager;
    private UsbDeviceConnection mDeviceConnection;
    private Context context;
    private UsbDevice mUsbDevice;
    private String strDevPath;
    private HashMap<String, UsbDevice> deviceList;
    private static final int PID = 0x0300;  //0300  CDROM, 0201 HID
    private static final int VID = 0x2796;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final String USBTAG = "wellcom";

    static {
        System.loadLibrary("WelFpV2");
    }


    public GfpUtils(Context context) {
        this.context = context;
    }

    /**
     * 打开设备
     *
     * @return 0 成功 -1 获取USB权限失败, -2 没有查询到USB设备, -3 USB设备连接失败, -4 USB设备打开失败
     */
    public int OpenDevice() {
        mManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(
                context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        deviceList = mManager.getDeviceList();
        int iFind = -2;
        //枚举设备
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            mUsbDevice = deviceIterator.next();
            if (mUsbDevice.getVendorId() == VID && mUsbDevice.getProductId() == PID) {
                if (!mManager.hasPermission(mUsbDevice)) {
                    mManager.requestPermission(mUsbDevice, mPermissionIntent);
                    if (!mManager.hasPermission(mUsbDevice))
                        return -1;       //获取USB权限失败
                }
                iFind = 1;
                break;
            }
        }
        if (iFind == 1) {
            mDeviceConnection = mManager.openDevice(mUsbDevice);

            if (mDeviceConnection != null) {
                fd = mDeviceConnection.getFileDescriptor();
                strDevPath = mUsbDevice.getDeviceName();
                if (fd != -1 && !strDevPath.isEmpty())
                    return 0;
                else
                    return -4;
            } else
                return -3;
        }

        return iFind;
    }

    /**
     * 设备检测
     *
     * @return
     */
    public String[] deviceDetect() {
        int iRet = 0;
        String[] str = new String[2];

        iRet = openWelDevice();
        if (iRet != 0) {
            str[0] = "-1";
            str[1] = "no open";
            return str;
        } else {
            str[0] = "0";
            str[1] = "deviceDetect ok";
            closeWelDevice();
            return str;
        }

    }

    /**
     * 打开WELL设备
     *
     * @return
     */
    public int openWelDevice() {
        int iRet;
        iRet = OpenDevice();

        Log.d(TAG, "openWelDevice OpenDevice ret =" + iRet);
        if (iRet == 0) {
            iRet = open(fd, strDevPath);
            Log.d(TAG, "openWelDevice open ret =" + iRet);
        }
        return iRet;
    }


    /*
     * getVersion
     * 检测设备
     * @return ： int, 0表示open成功， 非0 表示open失败
     */
    public int getWelVersion() {
        String[] verson = getVersion();
        Log.d(TAG, "finger getVersion : " + (!"0".equals(verson[0]) ? verson[0] : verson[1]));
        if (verson[1].equals("0")) {
            return 0;
        }
        return Integer.parseInt(verson[0]);
    }

    /**
     * 重新挂载WELL设备
     *
     * @return
     */
    public int reloadWelDevice() {
        int ret = reload();
        Log.d(TAG, "reloadWelDevice reload ret =" + ret);
        return ret;
    }

    /**
     * 关闭WELL设备
     *
     * @return
     */
    public int closeWelDevice() {
        int ret = close();
        Log.d(TAG, "closeWelDevice close ret =" + ret);

        if (mDeviceConnection != null) {
            mDeviceConnection.close();
            mDeviceConnection = null;
        }
        return ret;
    }


    /**
     * 重置WELL指纹仪
     *
     * @return
     */
    public int resetWelDevice() {
        int ret = -1;
        try {
            Log.d(TAG, "resetWelDevice reset start");
            ret = openWelDevice();
            Thread.sleep(100);
            ret = reloadWelDevice();
            Thread.sleep(4000);
            ret = openWelDevice();
            Thread.sleep(200);
            ret = getWelVersion();
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "resetWelDevice error = " + e.getMessage());
        }
        Log.d(TAG, "resetWelDevice reset end");
        return ret;
    }

    /*
     * open
     *  调用所有设备接口时需要先成功调用此接口
     * fd 设备标志   path 设备安卓路径
     * return 0  成功  其它失败
     * */
    public native int open(int fd, String path);

    /*
     * close
     * 关闭(结束操作后必须调用该方法)
     * *返回
     * int 0 成功 非0 失败 ,-2 已经关闭
     * */
    public native int close();

    //重新挂载USB设备  0 成功  非0 失败  -1未打开设备，请先打开设备；重现挂载后再次调用打开接口
    /*
     * reload
     * 重新挂载USB设备
     * *返回
     * int 0 成功  非0 失败  -1未打开设备，请先打开设备；重现挂载后再次调用打开接口
     * */
    public native int reload();

    /*
     * getVersion
     * 检测设备
     * * 返回
     * String[0] 操作结果
     * String[1] 版本信息/错误信息
     * */
    public native String[] getVersion();


    /*
     * captureImage
     * 采集图像
     * int iTime 超时时间
     * * 返回
     * String[0] 操作结果
     * String[1] 图像信息 (注意返回的为BASE64后的BMP图像)/错误信息
     * */
    public native String[] captureImage(int iTime);

    /*
     * FPI_fingerDetect
     * 获取手指状态
     * * 返回
     * String[0] 操作结果
     * String[1] 状态信息 0：抬起  1：按下/错误信息
     * */
    public native String[] fingerDetect();

    /*
     * getFeature
     * 采集特征
     * int iTime 超时时间
     * * 返回
     * String[0] 操作结果
     * String[1] 特征信息 特征信息/错误信息
     * */
    public native String[] getFeature(int iTime);

    /*
     * getTemplate
     * 采集模板
     * int iTime 超时时间
     * * 返回
     * String[0] 操作结果
     * String[1] 模板信息       模板信息/错误信息
     * */
    public native String[] getTemplate(int iTime);

    /* 判断特征数据质量
     * getFeatureQuality
     * 参数： strFtr 特征数据
     * 返回：
     * String[0] 操作结果
     * String[1] 质量信息/错误信息
     */
    public native String[] getFeatureQuality(String strFtr);

    /*
     * match
     * 比对 (1:1)
     * String strFtr1
     * String strFtr2
     * int ilevel 比对等级
     * * 返回
     * String[0] 操作结果
     * String[1] 比对分数/错误信息
     * */
    public native String[] match(String strFtr1, String strFtr2, int ilevel);

    /*指纹搜索比对
     * @param psTemplateBuf
     *            指纹模板数据数组
     * @param nTemplateCount
     *            模板数量，支持1~10000个指纹模板
     * @param psFeatureBuf
     *            指纹特征数据
     * @param nSecurityLevel
     *            安全级别（1~5，默认为3）
     * 返回：
     * 	* string[0]:返回码
     * string[1]:返回码信息
     * string[2]:比对通过的模板序号
     *
     */
    public native String[] searchMatch(byte[] psTemplateBuf, int nTemplateCount, byte[] psFeatureBuf);

}