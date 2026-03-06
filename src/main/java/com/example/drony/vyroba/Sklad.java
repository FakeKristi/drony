package com.example.drony.vyroba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.HashMap;

public class Sklad {
    private final HashMap<String, Integer> items = new HashMap<>();
    private ObservableList<Objednavka> data = FXCollections.observableArrayList();

    public synchronized void add(Objednavka[] itemToAdd) {
        for (Objednavka item : itemToAdd) {
            items.compute(
                    item.getItem(),
                    (key, value) ->
                            value == null ? item.getAmount() : value + item.getAmount()
            );
        }

        updateList();
    }

    public synchronized boolean take(Objednavka[] itemsToTake) {
        for (Objednavka item : itemsToTake) {
            if (!items.containsKey(item.getItem())) {
                return false;
            }
            if (items.get(item.getItem())-item.getAmount() < 0) {
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

        updateList();
        return true;
    }
    public synchronized boolean hasAll(Objednavka[] itemsCheck, int[] amounts) {
        for (int i = 0; i < itemsCheck.length; i++) {
            if (!items.containsKey(itemsCheck[i].getItem())) {
                return false;
            }
            if (items.get(itemsCheck[i].getItem()) < amounts[i]) {
                return false;
            }
        }

        return true;
    }

    public void setData(ObservableList<Objednavka> data) {
        this.data = data;
    }

    private void updateList() {
        data.clear();
        items.forEach((key, value) -> data.add(new Objednavka(key, value)));
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
