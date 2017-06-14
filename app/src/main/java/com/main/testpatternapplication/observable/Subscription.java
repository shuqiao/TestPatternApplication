package com.main.testpatternapplication.observable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class Subscription {
    public Reference<Object> subscriber;
    public TargetMethod targetMethod;

    public Subscription(Object subscriber, TargetMethod targetMethod) {
        this.subscriber = new WeakReference<>(subscriber);
        this.targetMethod = targetMethod;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
        result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subscription other = (Subscription) obj;
        if (subscriber.get() == null) {
            if (other.subscriber.get() != null)
                return false;
        } else if (!subscriber.get().equals(other.subscriber.get()))
            return false;
        if (targetMethod == null) {
            if (other.targetMethod != null)
                return false;
        } else if (!targetMethod.equals(other.targetMethod))
            return false;
        return true;
    }
}
