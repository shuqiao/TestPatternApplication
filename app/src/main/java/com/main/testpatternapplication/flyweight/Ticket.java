package com.main.testpatternapplication.flyweight;

import android.util.Log;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class Ticket {
    private final static String TAG = "Ticket";

    //可共用
    private String from;
    private String to;

    //不可共用
    private int bunk;//1-上铺，2-中铺，3-下铺，
    private int price;

    public Ticket(String from, String to) {
        Log.e(TAG, "new Ticket");
        this.from = from;
        this.to = to;
    }

    public void logInfo(int bunk) {
        price = bunk * 10;

        Log.e(TAG, "from-" + from + " , to-" + to + " , bunk-" + bunk + " , price-" + price);
    }
}
