package com.main.testpatternapplication.bridge;

/**
 * Created by shuqiao on 2017/6/14.
 */

public abstract class Coffee {
    public static final String TAG = "Coffee";

    protected CoffeeAdditives additives;

    public Coffee(CoffeeAdditives additives) {
        this.additives = additives;
    }

    public abstract void make();
}
