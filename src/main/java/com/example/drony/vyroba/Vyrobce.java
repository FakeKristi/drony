package com.example.drony.vyroba;

import java.util.List;

public class Vyrobce {
    Sklad sklad;
    Objednavka[] material;
    Objednavka[] product;
    Thread thread;

    public Vyrobce(Sklad sklad, Objednavka[] material, Objednavka[] product, Thread thread) {
        this.sklad = sklad;
        this.material = material;
        this.product = product;
        thread =  new Thread(this::vyrobit);
    }

    public void vyrobit() {
        try {
            if (sklad.take(items, amounts)) {
                    Thread.sleep(1000);
                sklad.add(product, producedAmount);
            }
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // return unused items
            throw new RuntimeException(e);
        }
    }
}
