package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;

/**
 * 单一职责原则
 * <p>
 * Created by shuqiao on 2017/6/6.
 */

public class ImageLoader {

    private ImageCache cache = new MemoryCache();
    private ImageDownload download = new ImageDownload();

    public Bitmap loadImage(String url) {
        Bitmap bitmap = cache.get(url);

        if (bitmap == null) {
            bitmap = download.downloadImage(url);
            if (bitmap != null) {
                cache.put(url, bitmap);
            }
        }

        return bitmap;
    }

    public void setCache(ImageCache cache) {
        this.cache = cache;
    }
}
