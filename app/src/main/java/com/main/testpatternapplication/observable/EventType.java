package com.main.testpatternapplication.observable;

/**
 * Created by shuqiao on 2017/6/13.
 */

public final class EventType {

    public static final String DEFAULT_TAG = "default_tag";

    public String tag = DEFAULT_TAG;
    public Class<?> clz;

    public EventType(Class<?> clz) {
        this(clz, DEFAULT_TAG);
    }

    public EventType(Class<?> clz, String tag) {
        this.clz = clz;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "EventType[ paramClass-" + clz.getName() + " , tag-" + tag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (clz == null ? 0 : clz.hashCode());
        result = prime * result + (tag == null ? 0 : tag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        EventType other = (EventType) obj;
        if (clz == null) {
            if (((EventType) obj).clz != null) {
                return false;
            }
        } else if (!clz.equals(((EventType) obj).clz)) {
            return false;
        }

        if (tag == null) {
            if (((EventType) obj).tag != null) {
                return false;
            }
        } else if (!tag.equals(((EventType) obj).tag)) {
            return false;
        }

        return true;
    }
}
