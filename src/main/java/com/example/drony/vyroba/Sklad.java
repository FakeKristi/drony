package com.example.drony.vyroba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Sklad {
    HashMap<String, Integer> items = new HashMap<>();


    public synchronized boolean add(Objednavka[] itemToAdd) {
        for (Objednavka item : itemToAdd) {
            items.compute(
                    item.getItem(),
                    (key, value) ->
                            value == null ? item.getAmount() : value + item.getAmount()
            );
        }


        return true;
    }

    public synchronized boolean take(Objednavka[] itemsToTake) {
        for (Objednavka item : itemsToTake) {
            if (items.get(item.getItem()) < 0) {
                return false;
            }
        }

        for (Objednavka item : itemsToTake) {
            items.compute(
                    item.getItem(),
                    (key, value) ->
                            value == null ? 0 : value - item.getAmount()
            );
        }

        return true;
    }


}
