package com.main.testpatternapplication.observable;

import java.lang.reflect.Method;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class TargetMethod {

    public Method method;
    public EventType eventType;
    public ThreadMode threadMode;

    public TargetMethod(Method md, EventType type, ThreadMode mode) {
        this.method = md;
        this.method.setAccessible(true);
        this.eventType = type;
        this.threadMode = mode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((method == null) ? 0 : method.getName().hashCode());
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
        TargetMethod other = (TargetMethod) obj;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.getName().equals(other.method.getName()))
            return false;
        return true;
    }

}
