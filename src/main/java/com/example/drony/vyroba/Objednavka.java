package com.example.drony.vyroba;

import javafx.beans.property.*;

public class Objednavka {
    private final StringProperty item;
    private final IntegerProperty amount;

    public Objednavka(String item, int amount) {
        this.item = new SimpleStringProperty(item);
        this.amount = new SimpleIntegerProperty(amount);
    }

    public StringProperty itemProperty() { return item; }
    public IntegerProperty amountProperty() { return amount; }

    public int getAmount() { return amount.get(); }
    public void setAmount(int value) { this.amount.set(value); }

    public String getItem() { return item.get(); }
}