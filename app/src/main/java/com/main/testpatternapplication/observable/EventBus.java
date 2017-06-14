package com.main.testpatternapplication.observable;

import com.main.testpatternapplication.observable.handler.AsyncEventHandler;
import com.main.testpatternapplication.observable.handler.DefaultEventHandler;
import com.main.testpatternapplication.observable.handler.EventHandler;
import com.main.testpatternapplication.observable.handler.UIEventHandler;
import com.main.testpatternapplication.observable.matcher.DefaultMatchPolicy;
import com.main.testpatternapplication.observable.matcher.MatchPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class EventBus {

    public static final String TAG = EventType.class.getSimpleName();

    private String mDesc = TAG;

    private final Map<EventType, CopyOnWriteArrayList<Subscription>> subscriberMap = new ConcurrentHashMap<>();

    private List<EventType> stickyEvents = Collections.synchronizedList(new LinkedList<EventType>());

    private ThreadLocal<Queue<EventType>> localEvents = new ThreadLocal<Queue<EventType>>() {
        @Override
        protected Queue<EventType> initialValue() {
            return new ConcurrentLinkedQueue<>();
        }
    };


    private class EventDispatcher {

        private EventHandler uiEventHandler = new UIEventHandler();

        private EventHandler postEventHandler = new DefaultEventHandler();

        private EventHandler ayyncEventHandler = new AsyncEventHandler();

        private Map<EventType, List<EventType>> cacheEventTypes = new ConcurrentHashMap<>();

        private MatchPolicy matchPolicy = new DefaultMatchPolicy();

        void dispatchEvent(Object event) {
            Queue<EventType> eventTypes = localEvents.get();

            while (eventTypes.size() > 0) {

            }
        }

        void deliveryEvent(EventType type, Object event) {
            List<EventType> eventTypes = getMatchedEventTypes(type, event);
            // 迭代所有匹配的事件并且分发给订阅者
            for (EventType eventType : eventTypes) {
                handleEvent(eventType, event);
            }
        }

        private List<EventType> getMatchedEventTypes(EventType type, Object aEvent) {
            List<EventType> eventTypes = null;
            // 如果有缓存则直接从缓存中取
            if (cacheEventTypes.containsKey(type)) {
                eventTypes = cacheEventTypes.get(type);
            } else {
                eventTypes = matchPolicy.findMatchEventTypes(type, aEvent);
                cacheEventTypes.put(type, eventTypes);
            }

            return eventTypes != null ? eventTypes : new ArrayList<EventType>();
        }

        private void handleEvent(EventType eventType, Object event) {
            List<Subscription> subsriptions = subscriberMap.get(eventType);

            if (subsriptions == null || subsriptions.size() == 0) {
                return;
            }

            for (Subscription s : subsriptions) {
                getEventHandler(s.targetMethod.threadMode).handleEvent(s, event);
            }
        }

        void dispatchStickyEvent(Object subscriber) {
            for (EventType eventType : stickyEvents) {
                handleStickyevent(eventType, subscriber);
            }
        }

        void handleStickyevent(EventType type, Object subscriber) {
            List<EventType> eventTypes = getMatchedEventTypes(type, type.clz);
            Object event = type.clz;

            for (EventType foundType : eventTypes) {
                List<Subscription> subscriptions = subscriberMap.get(foundType);
                if (subscriptions == null || subscriptions.size() == 0) {
                    continue;
                }

                for (Subscription sub : subscriptions) {
                    if (isTarget(sub, subscriber)
                            && sub.targetMethod.eventType.equals(foundType)
                            || sub.targetMethod.eventType.clz.isAssignableFrom(foundType.clz)) {
                        getEventHandler(sub.targetMethod.threadMode).handleEvent(sub, event);
                    }
                }
            }
        }

        private boolean isTarget(Subscription item, Object subscriber) {
            Object cacheObject = item.subscriber != null ? item.subscriber.get() : null;
            return subscriber == null || (subscriber != null
                    && cacheObject != null && cacheObject.equals(subscriber));
        }

        EventHandler getEventHandler(ThreadMode mode) {
            if (mode == ThreadMode.ASYNC)
                return ayyncEventHandler;

            if (mode == ThreadMode.POST)
                return postEventHandler;

            return uiEventHandler;
        }
    }


}

