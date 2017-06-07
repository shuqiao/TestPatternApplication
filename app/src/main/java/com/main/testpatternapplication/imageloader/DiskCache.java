package com.main.testpatternapplication.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.testpatternapplication.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shuqiao on 2017/6/6.
 */

public class DiskCache implements ImageCache {

    private static String CACHE_PATH;

    public DiskCache(Context context) {
        File dir = StorageUtils.getIndividualCacheDirectory(context, "image");

        if (!dir.exists()) {
            dir.mkdir();
        }

        CACHE_PATH = dir.getAbsolutePath();
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String filePath = getCachePath(url);

        FileOutputStream out = null;
        try {

            File file = new File(filePath);

            file.createNewFile();

            out = new FileOutputStream(filePath);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(getCachePath(url));
    }

    private String getCachePath(String url) {
        return CACHE_PATH + "/" + getCacheName(url);
    }

    private String getCacheName(String url) {
        String name = url.substring(0, url.length());
        return name.replace("/", "*");
    }
}
