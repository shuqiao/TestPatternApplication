package com.main.testpatternapplication.observable.matcher;

import com.main.testpatternapplication.observable.EventType;

import java.util.List;

/**
 * Created by shuqiao on 2017/6/13.
 */

public interface MatchPolicy {
    List<EventType> findMatchEventTypes(EventType type, Object event);
}
