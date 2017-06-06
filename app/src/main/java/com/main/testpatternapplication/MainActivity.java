package com.main.testpatternapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.main.testpatternapplication.imageloader.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Button btnLoad;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);
        btnLoad = (Button) findViewById(R.id.btn_load);

        imageLoader = new ImageLoader();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = imageLoader.loadImage("http://img08.oneniceapp.com/upload/show/2017/06/06/3105a10448e1e3e3d3eb38e366a76886.jpg");
                img.setImageBitmap(bitmap);
            }
        });
    }
}
