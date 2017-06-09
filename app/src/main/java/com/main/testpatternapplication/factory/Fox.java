package com.main.testpatternapplication.factory;

import android.util.Log;

/**
 * Created by shuqiao on 2017/6/9.
 */

public class Fox extends Animal {
    @Override
    public void talk() {
        Log.e(Animal.TAG, "I am a fox");
    }
}
