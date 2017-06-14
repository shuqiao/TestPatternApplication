package com.main.testpatternapplication.observable;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class SuscriberMethodHunter {
    private Map<EventType, CopyOnWriteArrayList<Subscription>> subscriberMap;

    public SuscriberMethodHunter(Map<EventType, CopyOnWriteArrayList<Subscription>> subscriberMap) {
        this.subscriberMap = subscriberMap;
    }

    public void findSubscriberMethods(Object subscriber) {
        if (subscriberMap == null) {
            return;
        }

        Class<?> clz = subscriber.getClass();

        while (clz != null && isSystemClass(clz.getName())) {
            final Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {

                Subscriber annotation = method.getAnnotation(Subscriber.class);

                if (annotation != null) {
                    Class<?>[] paramstypeClass = method.getParameterTypes();

                    if (paramstypeClass != null && paramstypeClass.length == 1) {
                        Class<?> paramType = convertType(paramstypeClass[0]);
                        EventType eventType = new EventType(paramType, annotation.tag());
                        TargetMethod targetMethod = new TargetMethod(method, eventType, annotation.mode());

                        subscribe(eventType, targetMethod, subscriber);
                    }
                }

            }

            clz = clz.getSuperclass();
        }
    }

    public void removeMethodsFromMap(Object subscriber) {
        Iterator<CopyOnWriteArrayList<Subscription>> iterator = subscriberMap.values().iterator();

        while (iterator.hasNext()) {
            CopyOnWriteArrayList<Subscription> subscriptions = iterator.next();

            if (subscriptions != null) {
                List<Subscription> foundSubscriptions = new LinkedList<>();
                Iterator<Subscription> subIterator = subscriptions.iterator();

                while (subIterator.hasNext()) {
                    Subscription subscription = subIterator.next();

                    Object cacheObj = subscription.subscriber.get();

                    if (isObjectsEqual(cacheObj, subscriber)) {
                        foundSubscriptions.add(subscription);
                    }
                }

                subscriptions.removeAll(foundSubscriptions);
            }

            if (subscriptions == null || subscriptions.size() == 0) {
                iterator.remove();
            }
        }

    }

    private boolean isObjectsEqual(Object cachedObj, Object subscriber) {
        return cachedObj != null
                && cachedObj.equals(subscriber);
    }

    private void subscribe(EventType eventType, TargetMethod targetMethod, Object subscriber) {
        CopyOnWriteArrayList<Subscription> subscriptions = subscriberMap.get(eventType);

        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
        }

        Subscription subscription = new Subscription(subscriber, targetMethod);
        if (subscriptions.contains(subscription)) {
            return;
        }

        subscriptions.add(subscription);

        subscriberMap.put(eventType, subscriptions);
    }

    private Class<?> convertType(Class<?> eventType) {
        Class<?> result = eventType;

        if (eventType.equals(boolean.class)) {
            result = Boolean.class;
        } else if (eventType.equals(int.class)) {
            result = Integer.class;
        } else if (eventType.equals(float.class)) {
            result = Float.class;
        } else if (eventType.equals(double.class)) {
            result = Double.class;
        }

        return result;
    }

    private boolean isSystemClass(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }
}
