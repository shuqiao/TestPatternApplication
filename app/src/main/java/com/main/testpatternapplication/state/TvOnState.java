package com.main.testpatternapplication.state;

import android.util.Log;

/**
 * Created by shuqiao on 2017/6/9.
 */

public class TvOnState extends SimpleTvState {

    @Override
    public void turnUp() {
        Log.e(TvState.TAG, "turnUp");
    }

    @Override
    public void turnDown() {
        Log.e(TvState.TAG, "turnDown");
    }

    @Override
    public void preChannel() {
        Log.e(TvState.TAG, "preChannel");
    }

    @Override
    public void nextChannel() {
        Log.e(TvState.TAG, "nextChannel");
    }
}
