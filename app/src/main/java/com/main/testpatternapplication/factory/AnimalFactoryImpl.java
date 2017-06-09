package com.main.testpatternapplication.factory;

/**
 * Created by shuqiao on 2017/6/9.
 */

public class AnimalFactoryImpl implements AnimalFactory {

    @Override
    public <T extends Animal> T createAnimal(Class<T> clz) {
        Animal animal = null;

        try {
            animal = (Animal) Class.forName(clz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T) animal;
    }

}

