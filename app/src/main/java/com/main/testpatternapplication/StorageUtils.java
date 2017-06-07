package com.main.testpatternapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.WorkerThread;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by skyfishjy on 3/25/15.
 */
public class StorageUtils {

    public static final String INDIVIDUAL_DIR_NAME = "mont-data";
    private static final String TAG = StorageUtils.class.getSimpleName();
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/nice_data")</i> if card is mounted and app has
     * appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context) {
        return getIndividualCacheDirectory(context, INDIVIDUAL_DIR_NAME);
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/nice_data")</i> if card is mounted and app has
     * appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    @WorkerThread
    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has appropriate permission) or
     * on device's file system depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Log.w(TAG, String.format("Can't define system cache directory! '%s' will be used.", cacheDirPath));
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(TAG, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    @WorkerThread
    public static File getIndividualFileDirectory(Context context, String fileDir) {
        File appFileDir = getFileDirectory(context);
        File individualFileDir = new File(appFileDir, fileDir);
        if (!individualFileDir.exists()) {
            if (!individualFileDir.mkdirs()) {
                individualFileDir = appFileDir;
            }
        }
        return individualFileDir;
    }

    public static File getFileDirectory(Context context) {
        File appFileDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appFileDir = context.getExternalFilesDir(null);
        }
        if (appFileDir == null) {
            appFileDir = context.getFilesDir();
        }
        if (appFileDir == null) {
            String fileDirPath = "/data/data/" + context.getPackageName() + "/file/";
            Log.w(TAG, String.format("Can't define system cache directory! '%s' will be used.", fileDirPath));
            appFileDir = new File(fileDirPath);
        }
        return appFileDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        if (context != null) {
            int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
            return perm == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @WorkerThread
    public static boolean cacheZipFileFromStream(InputStream is) throws Exception {
        return cacheZipFileFromStream("", is);
    }

    @WorkerThread
    public static boolean cacheZipFileFromStream(String prefix, InputStream is) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new NetworkOnMainThreadException();
        }

        boolean ret = false;
        ZipInputStream zipInputStream = null;

        try {
            zipInputStream = new ZipInputStream(is);
            for (ZipEntry zipEntry; (zipEntry = zipInputStream.getNextEntry()) != null; ) {
                String name = zipEntry.getName();
                Log.d(TAG, "CacheZipFileFromAssets from " + name + " Success");
            }

            ret = true;
        } catch (Throwable e) {
            e.printStackTrace();

            throw e;
        } finally {
            try {
                if (zipInputStream != null) {
                    zipInputStream.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static boolean cacheZipFileFromAssets(Context context, String filename) {
        AssetManager am = context.getApplicationContext().getResources().getAssets();
        try {
            InputStream is = am.open(filename + ".zip", AssetManager.ACCESS_STREAMING);
            ZipInputStream zipInputStream = new ZipInputStream(is);
            for (ZipEntry zipEntry; (zipEntry = zipInputStream.getNextEntry()) != null; ) {
                String name = zipEntry.getName();
                Log.d(TAG, "CacheZipFileFromAssets from " + name + " Success");
            }

            zipInputStream.close();
            return true;
        } catch (IOException e) {
            Log.d(TAG, "CacheZipFileFromAssets " + filename + " Error:" + e.getMessage());
            return false;
        } catch (NullPointerException nullPointerException) {
            Log.d(TAG, "CacheZipFileFromAssets " + filename + " NullPointerException:" + nullPointerException.getMessage());
            return false;
        }
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if (offset < 0) {
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if (len <= 0) {
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if (offset + len > (int) file.length()) {
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // ´´½¨ºÏÊÊÎÄ¼þ´óÐ¡µÄÊý×é
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    public static byte[] readFromFile(File file) {
        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(file, "r");
            b = new byte[(int) file.length()]; // ´´½¨ºÏÊÊÎÄ¼þ´óÐ¡µÄÊý×é
            in.seek(0);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

}