package com.main.testpatternapplication.single;

/**
 * 单例模式
 * 优：默认枚举实例的创建是线程安全的，抗反序列化
 * <p>
 * Created by shuqiao on 2017/6/8.
 */

public enum SingleFifth {
    INSTANCE;

    public void doSmething() {

    }
}
