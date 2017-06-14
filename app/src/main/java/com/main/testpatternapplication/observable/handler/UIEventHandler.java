package com.main.testpatternapplication.observable.handler;

import android.os.Handler;
import android.os.Looper;

import com.main.testpatternapplication.observable.Subscription;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class UIEventHandler implements EventHandler {

    private Handler handler = new Handler(Looper.getMainLooper());

    private DefaultEventHandler defaultEventHandler = new DefaultEventHandler();

    @Override
    public void handleEvent(final Subscription subscription, final Object event) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                defaultEventHandler.handleEvent(subscription, event);
            }
        });
    }
}
