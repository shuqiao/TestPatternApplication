package com.main.testpatternapplication.single;

/**
 * 静态内部类
 * 优：使用时才创建，无同步消耗，保证单例，推荐使用
 * <p>
 * Created by shuqiao on 2017/6/8.
 */

public class SingleFourth {

    private SingleFourth() {

    }

    public static SingleFourth getInstance() {
        return SingleFourthHolder.instance;
    }

    private static class SingleFourthHolder {
        private static final SingleFourth instance = new SingleFourth();
    }
}
