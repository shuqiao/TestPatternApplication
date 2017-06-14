package com.main.testpatternapplication.proxy;

import android.util.Log;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class PersonA implements ILawsuit {

    @Override
    public void submit() {
        Log.e(ILawsuit.TAG, "PersonA , submit");
    }

    @Override
    public void burden() {
        Log.e(ILawsuit.TAG, "PersonA , burden");
    }

    @Override
    public void defend() {
        Log.e(ILawsuit.TAG, "PersonA , defend");
    }

    @Override
    public void finish() {
        Log.e(ILawsuit.TAG, "PersonA , finish");
    }
}
