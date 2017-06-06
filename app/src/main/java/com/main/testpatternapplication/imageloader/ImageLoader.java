package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;

/**
 * 单一职责原则
 * <p>
 * Created by shuqiao on 2017/6/6.
 */

public class ImageLoader {

    private ImageCache cache;
    private ImageDownload download;

    public ImageLoader() {
        init();
    }

    private void init() {
        cache = new ImageCache();
        download = new ImageDownload();
    }

    public Bitmap loadImage(String url) {
        Bitmap bitmap = cache.getCacheImage(url);

        if (bitmap == null) {
            bitmap = download.downloadImage(url);
            if (bitmap != null) {
                cache.cacheImage(url, bitmap);
            }
        }

        return bitmap;
    }
}
