package com.main.testpatternapplication.observable.matcher;

import com.main.testpatternapplication.observable.EventType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class StrictMatchPolicy implements MatchPolicy {
    @Override
    public List<EventType> findMatchEventTypes(EventType type, Object event) {
        List<EventType> eventTypeList = new LinkedList<>();
        eventTypeList.add(new EventType(event.getClass(), type.tag));
        return eventTypeList;
    }
}
