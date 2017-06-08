package com.main.testpatternapplication.single;

/**
 * 懒汉式
 * <p>
 * 优：只在需要时创建
 * 缺：每次getInstance都需要synchronous同步，没有必要且效率低
 * <p>
 * Created by shuqiao on 2017/6/8.
 */

public class SingleSecond {

    private static SingleSecond instance;

    private SingleSecond() {

    }

    public synchronized static SingleSecond getInstance() {
        if (instance == null) {
            instance = new SingleSecond();
        }

        return instance;
    }
}
