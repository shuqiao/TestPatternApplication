package com.main.testpatternapplication.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by shuqiao on 2017/6/6.
 */

public class ImageDownload {

    private ExecutorService executor;

    public ImageDownload(int threadCount) {
        init(threadCount);
    }

    private void init(int threadCount) {
        executor = Executors.newFixedThreadPool(threadCount);
    }

    public Bitmap downloadImage(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }

        Future<Bitmap> result = executor.submit(new DownloadCallable(imageUrl));

        try {
            return result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class DownloadCallable implements Callable<Bitmap> {

        private String imageUrl;

        public DownloadCallable(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public Bitmap call() throws Exception {
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
