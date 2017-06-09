package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;

/**
 * Builder模式
 * 单一职责原则
 * <p>
 * Created by shuqiao on 2017/6/6.
 */

public class ImageLoader {

    private Bitmap errorBitmap;

    private ImageCache cache = new MemoryCache();
    private ImageDownload download = new ImageDownload(Runtime.getRuntime().availableProcessors());

    public ImageLoader(Builder builder) {
        if (builder != null) {
            cache = builder.cache;
            download = new ImageDownload(builder.downloadThreadCount);
            errorBitmap = builder.errorBitmap;
        }
    }

    public Bitmap loadImage(String url) {
        Bitmap bitmap = cache.get(url);

        if (bitmap == null) {
            bitmap = download.downloadImage(url);
            if (bitmap != null) {
                cache.put(url, bitmap);
            }
        }

        if (bitmap != null) {
            return bitmap;
        } else {
            return errorBitmap;
        }
    }
//
//    /**
//     * 依赖注入
//     */
//    public void setCache(ImageCache cache) {
//        this.cache = cache;
//    }

    public static class Builder {
        private ImageCache cache = new MemoryCache();
        private Bitmap errorBitmap = null;
        private int downloadThreadCount = Runtime.getRuntime().availableProcessors();

        public Builder cache(ImageCache cache) {
            this.cache = cache;
            return this;
        }

        public Builder errorBitmap(Bitmap errorBitmap) {
            this.errorBitmap = errorBitmap;
            return this;
        }

        public Builder downloadThreadCount(int downloadThreadCount) {
            this.downloadThreadCount = downloadThreadCount;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

}
