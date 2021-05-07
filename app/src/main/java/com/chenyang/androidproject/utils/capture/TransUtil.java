package com.chenyang.androidproject.utils.capture;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransUtil {

    public static void bitmap2File(Bitmap bitmap, String path) {
        try {
            OutputStream os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            Log.d("bitmap2File", "保存成功：" + path);
        } catch (Exception e) {
            Log.e("TAG", "", e);
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**
     * base64字符串转文件
     *
     * @param base64Code
     * @param savePath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        try {
            byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
            FileOutputStream out = new FileOutputStream(savePath);
            out.write(buffer);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        StringBuilder sb = new StringBuilder("");
        try {
            File file = new File(fileName);
            //打开文件输入流
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            //读取文件内容
            while (len > 0) {
                sb.append(new String(buffer, 0, len));
                //继续将数据放到buffer中
                len = inputStream.read(buffer);
            }
            //关闭输入流
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public static void saveFile(String str, String filePath) {

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
