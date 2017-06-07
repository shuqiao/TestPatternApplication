package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shuqiao on 2017/6/6.
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> bitmapLruCache;
    private ReentrantReadWriteLock lock;

    public MemoryCache() {
        init();
    }

    private void init() {
        final int max = (int) Runtime.getRuntime().maxMemory();
        bitmapLruCache = new LruCache<String, Bitmap>(max / 4) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        lock.writeLock().lock();

        if (bitmapLruCache.get(url) == null) {
            bitmapLruCache.put(url, bitmap);
        }

        lock.writeLock().unlock();
    }

    @Override
    public Bitmap get(String url) {
        lock.readLock().lock();

        Bitmap bitmap = bitmapLruCache.get(url);

        lock.readLock().unlock();

        return bitmap;
    }
}
