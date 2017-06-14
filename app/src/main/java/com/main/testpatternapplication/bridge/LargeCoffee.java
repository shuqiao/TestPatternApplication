package com.main.testpatternapplication.bridge;

import android.util.Log;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class LargeCoffee extends Coffee {

    public LargeCoffee(CoffeeAdditives additives) {
        super(additives);
    }

    @Override
    public void make() {
        String add = additives.add();

        Log.e(TAG, "LargeCoffee with " + add);
    }
}
