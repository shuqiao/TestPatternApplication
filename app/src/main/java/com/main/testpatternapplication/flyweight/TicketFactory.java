package com.main.testpatternapplication.flyweight;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class TicketFactory {

    private static Map<String, Ticket> ticketMap = new ConcurrentHashMap<>();

    public synchronized static Ticket getTicket(String from, String to) {
        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(to)) {
            return null;
        }

        String key = from + "-" + to;

        if (ticketMap.containsKey(key)) {
            return ticketMap.get(key);
        } else {
            Ticket ticket = new Ticket(from, to);
            ticketMap.put(key, ticket);
            return ticket;
        }
    }

    public static void clear() {
        ticketMap.clear();
    }
}
