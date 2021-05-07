package com.chenyang.androidproject.utils.capture;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;

public class FaceHelper {
    private static final String LOG_TAG = "FaceHelper";
    private static final boolean DEBUG_ENABLE = true;

    public static Bitmap genFaceBitmap(Bitmap sourceBitmap) {
        if (!checkBitmap(sourceBitmap, "genFaceBitmap()")) {
            return null;
        }
        Bitmap cacheBitmap = sourceBitmap.copy(Bitmap.Config.RGB_565, false);
        if (DEBUG_ENABLE) {
            Log.i(LOG_TAG, "genFaceBitmap() : source bitmap width - " + cacheBitmap.getWidth() + " , height - " + cacheBitmap.getHeight());
        }
        int cacheWidth = cacheBitmap.getWidth();
        int cacheHeight = cacheBitmap.getHeight();
        if (cacheWidth % 2 != 0) {
            if (0 == cacheWidth - 1) {
                if (DEBUG_ENABLE) {
                    Log.e(LOG_TAG,
                            "genFaceBitmap() : source bitmap width is only 1 , return null.");
                }
                return null;
            }
            final Bitmap localCacheBitmap = Bitmap.createBitmap(cacheBitmap, 0, 0, cacheWidth - 1, cacheHeight);
            cacheBitmap.recycle();
            cacheBitmap = localCacheBitmap;
            --cacheWidth;
            if (DEBUG_ENABLE) {
                Log.i(LOG_TAG, "genFaceBitmap() : source bitmap width - " + cacheBitmap.getWidth() + " , height - " + cacheBitmap.getHeight());
            }
        }
        final FaceDetector.Face[] faces = new FaceDetector.Face[1];
        final int facefound = new FaceDetector(cacheWidth, cacheHeight, 1).findFaces(cacheBitmap, faces);
        if (DEBUG_ENABLE) {
            Log.i(LOG_TAG, "genFaceBitmap() : facefound - " + facefound);
        }
        if (0 == facefound) {
            if (DEBUG_ENABLE) {
                Log.e(LOG_TAG, "genFaceBitmap() : no face found , return null.");
            }
            return null;
        }
        final PointF p = new PointF();
        faces[0].getMidPoint(p);
        if (DEBUG_ENABLE) {
            Log.i(LOG_TAG, "getFaceBitmap() : confidence - " + faces[0].confidence());
            Log.i(LOG_TAG, "genFaceBitmap() : face center x - " + p.x + " , y - " + p.y);
        }
        final int faceX = (int) p.x;
        final int faceY = (int) p.y;
        if (DEBUG_ENABLE) {
            Log.i(LOG_TAG, "genFaceBitmap() : int faceX - " + faceX + " , int faceY - " + faceY);
        }
        int startX, startY, width, height;
        if (faceX <= cacheWidth - faceX) {
            startX = 0;
            width = faceX * 2;
        } else {
            startX = faceX - (cacheWidth - faceX);
            width = (cacheWidth - faceX) * 2;
        }
        if (faceY <= cacheHeight - faceY) {
            startY = 0;
            height = faceY * 2;
        } else {
            startY = faceY - (cacheHeight - faceY);
            height = (cacheHeight - faceY) * 2;
        }
        final Bitmap result = Bitmap.createBitmap(cacheBitmap, startX, startY, 480, height);
        cacheBitmap.recycle();
        int w = result.getWidth(); // 得到图片的宽，高
        int h = result.getHeight();
        Log.e(LOG_TAG, " genFaceBitmap()-w :" + w + ",h:" + h);
        return result;
    }

    private static boolean checkBitmap(final Bitmap bitmap, final String debugInfo) {
        if (null == bitmap || bitmap.isRecycled()) {
            if (DEBUG_ENABLE) {
                Log.e(LOG_TAG, debugInfo + " : check bitmap , is null or is recycled.");
            }
            return false;
        }
        if (0 == bitmap.getWidth() || 0 == bitmap.getHeight()) {
            if (DEBUG_ENABLE) {
                Log.e(LOG_TAG, debugInfo + " : check bitmap , width is 0 or height is 0.");
            }
            return false;
        }
        return true;
    }
}
