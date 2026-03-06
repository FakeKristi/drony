package com.example.drony.vyroba;

import com.example.drony.vyroba.producers.GenericProducer;
import com.example.drony.vyroba.producers.IProducer;

import java.util.Random;

public class RandomProducer extends GenericProducer implements IProducer {
    public RandomProducer(Sklad sklad, Objednavka[] material, Objednavka[] product, int buildTime, int waitTime, String produceMessage, int[] limit, String name, Runnable onFinish) {
        super(sklad, material, product, buildTime, waitTime, produceMessage, limit, name, onFinish);
    }

    Objednavka[] actualProducts;

    Random rand  = new Random();
    @Override
    public void vyrobit() {
        while (!Thread.interrupted()) {
            if (!sklad.hasAll(product, limit)) {
                System.out.println("Vyrabim: " + product[0].getItem());
                if (sklad.take(material)) {
                    try {
                        Thread.sleep(buildTime);
                    } catch (InterruptedException e) {
                        sklad.add(material);
                        return;
                    }

                    actualProducts =  new Objednavka[product.length];
                    for (int i = 0; i < actualProducts.length; i++) {
                        actualProducts[i] = new Objednavka(product[i].getItem(), rand.nextInt(product[i].getAmount())+1);
                    }


                    sklad.add(actualProducts);
                    count++;
                    Logger.println(Thread.currentThread().getName(), produceMessage + ": " + printAmounts());
                    Logger.println(Thread.currentThread().getName(), sklad.toString());
                } else {
                    Logger.println(Thread.currentThread().getName(), "čeká na materiál pro: " + product[0].getItem() + "");
                    try {
                        Thread.sleep(waittTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            } else {
                onFinish.run();
                Logger.println(Thread.currentThread().getName(), "pozastavil výrobu: " + product[0].getItem());
                try {
                    Thread.sleep(waittTime);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private String printAmounts() {
        StringBuilder sb = new StringBuilder();
        for (Objednavka o : actualProducts) {
            sb.append(o.getAmount()).append(" ").append(o.getItem()).append(" (celkem=").append(o.getAmount() * count).append("), ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

}
