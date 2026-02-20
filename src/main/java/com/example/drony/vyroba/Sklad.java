package com.example.drony.vyroba;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Sklad {
    ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();


    public synchronized void add(String item, int amount) {
        map.put(item, amount);
    }

    public synchronized void take(String[] items, int[] amounts) {
        if (items.length != amounts.length) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < items.length; i++) {
            int finalI = i;
            map.putIfAbsent(items[i], 0);
            map.compute(items[finalI],(string, integer) -> integer+amounts[finalI]);

        }
    }


}
