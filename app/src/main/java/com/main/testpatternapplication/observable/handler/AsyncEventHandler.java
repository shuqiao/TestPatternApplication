package com.main.testpatternapplication.observable.handler;

import android.os.Handler;
import android.os.HandlerThread;

import com.main.testpatternapplication.observable.Subscription;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class AsyncEventHandler implements EventHandler {

    private DispatcherThread dispatcherHandler;

    private DefaultEventHandler defaultEventHandler = new DefaultEventHandler();

    public AsyncEventHandler() {
        dispatcherHandler = new DispatcherThread(AsyncEventHandler.class.getSimpleName());
        dispatcherHandler.start();
    }

    @Override
    public void handleEvent(final Subscription subscription, final Object event) {
        dispatcherHandler.post(new Runnable() {
            @Override
            public void run() {
                defaultEventHandler.handleEvent(subscription, event);
            }
        });
    }

    private static class DispatcherThread extends HandlerThread {

        private Handler handler;

        public DispatcherThread(String name) {
            super(name);
        }


        public void post(Runnable runnable) {
            if (handler == null) {
                return;
            }

            handler.post(runnable);
        }


        @Override
        public synchronized void start() {
            super.start();
            handler = new Handler(this.getLooper());
        }
    }
}
