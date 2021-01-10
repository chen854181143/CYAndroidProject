package com.chenyang.androidproject.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.WatermarkSettings;

import java.io.File;

/**
 * 图片添加水印
 */
public class ImageAddWatermarkActivity extends MyActivity {

    private static String filePath = Environment.getExternalStorageDirectory() + "/WatermarkPicture/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_add_watermark;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void addWatermark(View view){
        String photoAddress = "海淀区-清河路-西三旗桥";
        String phoneDate = "2019-01-22";
        String illicitCode = "1008";
        String illicitBehavior = "闯红灯";
        String equipmentNumber = "fgf54327d";
        String antifakeInformation = "*#4%6&*@";
        AppCompatImageView imageView = findViewById(R.id.image);
        int resource = R.drawable.meijing;
        WatermarkSettings.getmInstance(this);
        Bitmap bitmap = WatermarkSettings.createWatermark(resource, photoAddress, phoneDate, illicitCode, illicitBehavior, equipmentNumber, antifakeInformation);
        imageView.setImageBitmap(bitmap);
        //保存图片
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file=WatermarkSettings.savaWaterparkFile(filePath);
                Toast.makeText(getActivity(), "您点击了图片,已保存", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
