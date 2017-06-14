package com.main.testpatternapplication.observable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shuqiao on 2017/6/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscriber {
    String tag() default EventType.DEFAULT_TAG;

    ThreadMode mode() default ThreadMode.POST;
}
