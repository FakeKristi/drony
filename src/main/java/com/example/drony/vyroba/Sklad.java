package com.example.drony.vyroba;

import java.util.HashMap;

public class Sklad {
    private final HashMap<String, Integer> items = new HashMap<>();


    public synchronized void add(Objednavka[] itemToAdd) {
        for (Objednavka item : itemToAdd) {
            items.compute(
                    item.getItem(),
                    (key, value) ->
                            value == null ? item.getAmount() : value + item.getAmount()
            );
        }


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
