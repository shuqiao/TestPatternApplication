package com.main.testpatternapplication.command;

import android.util.Log;

/**
 * 真正执行者
 * <p>
 * Created by shuqiao on 2017/6/12.
 */

public class TetrisMachine {
    public static final String TAG = "TetrisMachine";

    public void left() {
        Log.e(TAG, "left");
    }

    public void right() {
        Log.e(TAG, "right");
    }

    public void up() {
        Log.e(TAG, "up");
    }

    public void down() {
        Log.e(TAG, "down");
    }
}
