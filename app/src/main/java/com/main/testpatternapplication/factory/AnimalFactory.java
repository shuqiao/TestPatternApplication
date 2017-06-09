package com.main.testpatternapplication.factory;

/**
 * Created by shuqiao on 2017/6/9.
 */

public interface AnimalFactory {

    <T extends Animal> T createAnimal(Class<T> animal);
}
