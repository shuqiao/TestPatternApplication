package com.main.testpatternapplication.single;

/**
 * 饿汉模式
 * <p>
 * 优：线程安全
 * 缺点：提前创建，造成资源占用及消耗
 * <p>
 * Created by shuqiao on 2017/6/8.
 */

public class SingleFirst {

    private static SingleFirst instance = new SingleFirst();

    private SingleFirst() {
    }

    public static SingleFirst getInstance() {
        return instance;
    }
}
