package com.main.testpatternapplication.single;

/**
 * 双重锁判断
 * 优：需要时才加载，避免了不必要的同步
 * 缺：有些情况下无法单例
 * <p>
 * Created by shuqiao on 2017/6/8.
 */

public class SingleThird {

    private static SingleThird instance;

    private SingleThird() {

    }

    public static SingleThird getInstance() {
        if (instance == null) {//避免不必要的同步
            synchronized (SingleThird.class) {
                if (instance == null) {
                    instance = new SingleThird();//在null条件下才创建
                }
            }
        }

        return instance;
    }
}
