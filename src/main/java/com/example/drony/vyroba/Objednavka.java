package com.example.drony.vyroba;

public class Objednavka {
    private String item;
    private int amount;

    public Objednavka(String item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
