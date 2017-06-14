package com.main.testpatternapplication.observable.matcher;

import com.main.testpatternapplication.observable.EventType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class DefaultMatchPolicy implements MatchPolicy {
    @Override
    public List<EventType> findMatchEventTypes(EventType type, Object event) {
        List<EventType> eventTypeList = new LinkedList<>();

        Class<?> eventClass = event.getClass();
        while (eventClass != null) {
            eventTypeList.add(new EventType(eventClass, type.tag));
            addInterfaces(eventTypeList, eventClass, type.tag);
            eventClass = eventClass.getSuperclass();
        }

        return eventTypeList;
    }


    private void addInterfaces(List<EventType> eventTypeList, Class<?> eventClass, String tag) {
        if (eventClass == null) {
            return;
        }

        Class<?>[] interfaceClasses = eventClass.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (!eventTypeList.contains(interfaceClass)) {
                eventTypeList.add(new EventType(interfaceClass, tag));
                addInterfaces(eventTypeList, interfaceClass, tag);
            }
        }
    }

}
