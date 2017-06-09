package com.main.testpatternapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.main.testpatternapplication.imageloader.DiskCache;
import com.main.testpatternapplication.imageloader.DoubleCache;
import com.main.testpatternapplication.imageloader.ImageCache;
import com.main.testpatternapplication.imageloader.ImageLoader;
import com.main.testpatternapplication.imageloader.MemoryCache;
import com.main.testpatternapplication.original.Document;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private ImageView img;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------------imageloader
        img = (ImageView) findViewById(R.id.img);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);

        final ImageCache memoryCache = new MemoryCache();
        final ImageCache diskCache = new DiskCache(this);
        final ImageCache doubleCache = new DoubleCache(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader = new ImageLoader.Builder().cache(memoryCache).downloadThreadCount(8).errorBitmap(null).build();
                Bitmap bitmap = imageLoader.loadImage("http://img08.oneniceapp.com/upload/show/2017/06/06/3105a10448e1e3e3d3eb38e366a76886.jpg");
                img.setImageBitmap(bitmap);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader = new ImageLoader.Builder().cache(diskCache).downloadThreadCount(8).errorBitmap(null).build();
                Bitmap bitmap = imageLoader.loadImage("http://img08.oneniceapp.com/upload/show/2017/06/06/7246ca4978e3435a47f6895d114de505.jpg");
                img.setImageBitmap(bitmap);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader = new ImageLoader.Builder().cache(doubleCache).downloadThreadCount(8).errorBitmap(null).build();
                Bitmap bitmap = imageLoader.loadImage("http://img08.oneniceapp.com/upload/show/2017/05/16/220e7e58890b55e8d5ef59287359e9b0.jpg");
                img.setImageBitmap(bitmap);
            }
        });

        //--------------original
        testCopy();

    }

    /**
     * 测试原型模式：clone or 深拷贝 or 浅拷贝
     */
    private void testCopy() {
        Document ori = new Document();
        ori.setText("ori");
        ori.setImg("ori img-1");
        ori.setImg("ori img-2");
        ori.setImg("ori img-3");

        Document clone = ori.clone();

        Log.e(TAG, "ori  \n" + ori.toString());
        Log.e(TAG, "clone  \n" + clone.toString());

        ori.setText("new ori");

        Log.e(TAG, "change ori text , ori  \n" + ori.toString());
        Log.e(TAG, "change ori text , clone  \n" + clone.toString());

        clone.setImg("clone img-1");

        Log.e(TAG, "change clone img , ori  \n" + ori.toString());
        Log.e(TAG, "change clone img , clone  \n" + clone.toString());
    }
}
