package com.main.testpatternapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.main.testpatternapplication.factory.AnimalFactory;
import com.main.testpatternapplication.factory.AnimalFactoryImpl;
import com.main.testpatternapplication.factory.Cat;
import com.main.testpatternapplication.factory.Dog;
import com.main.testpatternapplication.factory.Fox;
import com.main.testpatternapplication.imageloader.DiskCache;
import com.main.testpatternapplication.imageloader.DoubleCache;
import com.main.testpatternapplication.imageloader.ImageCache;
import com.main.testpatternapplication.imageloader.ImageLoader;
import com.main.testpatternapplication.imageloader.MemoryCache;
import com.main.testpatternapplication.memento.NoteCaretaker;
import com.main.testpatternapplication.memento.NoteEditText;
import com.main.testpatternapplication.original.Document;
import com.main.testpatternapplication.state.TV;
import com.main.testpatternapplication.state.TvState;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private ImageView img;
    private ImageLoader imageLoader;

    private NoteEditText etNote;
    private Button btnCancel;
    private Button btnSave;
    private Button btnRestore;
    private NoteCaretaker noteCaretaker;

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------------imageloader
        //testImageLoader();

        //--------------original
        //testCopy();

        //--------------factory
        //testFactory();

        //--------------state
        //testState();

        //--------------memento
        //testMemento();

        //--------------mediator
        testMediator();
    }

    private void testMediator() {
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
        setClickable(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();

                setClickable(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password));
            }
        };

        etName.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }

    private void setClickable(boolean clickable) {
        btnLogin.setClickable(clickable);
        btnLogin.setTextColor(getResources().getColor(clickable ? R.color.normal_text_color : R.color.light_text_color));
    }

    private void testMemento() {
        noteCaretaker = new NoteCaretaker(5);

        etNote = (NoteEditText) findViewById(R.id.et_note);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnRestore = (Button) findViewById(R.id.btn_restore);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNote.restoreMemento(noteCaretaker.pre());
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteCaretaker.save(etNote.getSaveMemento());
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNote.restoreMemento(noteCaretaker.next());
            }
        });
    }

    private void testImageLoader() {
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

    private void testFactory() {
        AnimalFactory animalFactory = new AnimalFactoryImpl();

        animalFactory.createAnimal(Cat.class).talk();

        animalFactory.createAnimal(Dog.class).talk();

        animalFactory.createAnimal(Fox.class).talk();
    }

    private void testState() {
        TV tv = new TV();
        Log.e(TvState.TAG, "not on");
        tv.next();
        tv.on();
        Log.e(TvState.TAG, "on");
        tv.down();
        tv.off();
        Log.e(TvState.TAG, "off");
        tv.pre();
    }
}
