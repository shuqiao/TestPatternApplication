package com.main.testpatternapplication.observable.handler;

import com.main.testpatternapplication.observable.Subscription;

/**
 * Created by shuqiao on 2017/6/13.
 */

public interface EventHandler {
    void handleEvent(Subscription subscription, Object event);
}
