package com.main.testpatternapplication.state;

/**
 * Created by shuqiao on 2017/6/9.
 */

public interface TvState {
    String TAG = "TvState";
    
    void turnUp();

    void turnDown();

    void preChannel();

    void nextChannel();
}
