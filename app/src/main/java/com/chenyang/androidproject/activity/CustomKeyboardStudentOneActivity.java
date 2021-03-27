package com.chenyang.androidproject.activity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.CarKeyBoardUtil;
import com.chenyang.androidproject.view.keyboard.CarKeyboardView;

/**
 * 自定义键盘学习一
 */
public class CustomKeyboardStudentOneActivity extends MyActivity {

    private EditText act_key_board_et;
    private CarKeyboardView keyboardView;
    private View ky_keyboard_parent;
    private CarKeyBoardUtil carKeyBoardUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.custom_keyboard_student_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        act_key_board_et = findViewById(R.id.act_key_board_et);
        keyboardView = findViewById(R.id.ky_keyboard);
        ky_keyboard_parent = findViewById(R.id.ky_keyboard_parent);
        carKeyBoardUtil = new CarKeyBoardUtil(ky_keyboard_parent,keyboardView,act_key_board_et);
        act_key_board_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (carKeyBoardUtil == null) {
                    carKeyBoardUtil = new CarKeyBoardUtil(ky_keyboard_parent,keyboardView,act_key_board_et);
                }
                carKeyBoardUtil.showKeyboard();
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }
}
