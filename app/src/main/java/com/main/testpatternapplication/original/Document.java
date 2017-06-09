package com.main.testpatternapplication.original;

import android.util.Log;

import java.util.ArrayList;

/**
 * 原型模式：实现Cloneable接口的形式
 * 三个条件：
 * 1>实现Cloneable接口
 * 2>提供一个public的clone方法，即重写Object.clone方法
 * 3>若为深拷贝，需对可变域进行拷贝，eg.imgList
 * <p>
 * Created by shuqiao on 2017/6/9.
 */

public class Document implements Cloneable {
    public static final String TAG = "Document";

    private String text = "";
    private ArrayList<String> imgList = new ArrayList<>();

    public Document() {
        Log.e(TAG, "Document 构造函数");
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        imgList.add(img);
    }

    /**
     * 原拷贝，是一种浅拷贝
     */
    @Override
    public Document clone() {
        try {
            Document document = (Document) super.clone();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 浅拷贝
     */
//    @Override
//    public Document clone() {
//        try {
//            Document document = (Document) super.clone();
//            document.text = text;
//            document.imgList = imgList;
//            return document;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * 深拷贝
     */
//    @Override
//    public Document clone() {
//        try {
//            Document document = (Document) super.clone();
//            document.text = text;
//            document.imgList = (ArrayList<String>) imgList.clone();
//            return document;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("-----------");
        info.append("\n");
        info.append(text);
        info.append("\n");
        if (imgList.size() > 0) {
            for (String img : imgList) {
                info.append(img);
                info.append("\n");
            }
        }
        info.append("-----------");
        info.append("\n");
        return info.toString();
    }
}
