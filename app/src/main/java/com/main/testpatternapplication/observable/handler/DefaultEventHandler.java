package com.main.testpatternapplication.observable.handler;

import com.main.testpatternapplication.observable.Subscription;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class DefaultEventHandler implements EventHandler {

    @Override
    public void handleEvent(Subscription subscription, Object event) {
        if (subscription == null || subscription.subscriber.get() == null) {
            return;
        }

        try {
            subscription.targetMethod.method.invoke(subscription.subscriber.get(), event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
