package com.main.testpatternapplication.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by shuqiao on 2017/6/6.
 */

public class DoubleCache implements ImageCache {

    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public DoubleCache(Context context) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(context);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);

        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }

        return bitmap;
    }
}
