package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;

/**
 * Created by shuqiao on 2017/6/6.
 */

public interface ImageCache {
    void put(String url, Bitmap bitmap);

    Bitmap get(String url);
}
