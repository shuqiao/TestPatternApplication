package com.main.testpatternapplication.imageloader;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by shuqiao on 2017/6/8.
 */

public class CloseUtil {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
