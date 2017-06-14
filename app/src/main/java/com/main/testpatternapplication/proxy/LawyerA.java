package com.main.testpatternapplication.proxy;

import android.util.Log;

/**
 * 静态代理
 * Created by shuqiao on 2017/6/14.
 */

public class LawyerA implements ILawsuit {

    private ILawsuit lawsuit;

    public LawyerA(ILawsuit lawsuit) {
        this.lawsuit = lawsuit;
    }

    @Override
    public void submit() {
        Log.e(ILawsuit.TAG, "LawyerA , submit");

        if (lawsuit != null) {
            lawsuit.submit();
        }
    }

    @Override
    public void burden() {
        Log.e(ILawsuit.TAG, "LawyerA , burden");

        if (lawsuit != null) {
            lawsuit.submit();
        }
    }

    @Override
    public void defend() {
        Log.e(ILawsuit.TAG, "LawyerA , defend");

        if (lawsuit != null) {
            lawsuit.submit();
        }
    }

    @Override
    public void finish() {
        Log.e(ILawsuit.TAG, "LawyerA , finish");

        if (lawsuit != null) {
            lawsuit.submit();
        }
    }
}
