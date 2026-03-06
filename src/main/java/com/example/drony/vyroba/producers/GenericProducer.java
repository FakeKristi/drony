package com.example.drony.vyroba.producers;

import com.example.drony.vyroba.Logger;
import com.example.drony.vyroba.Objednavka;
import com.example.drony.vyroba.Sklad;

public class GenericProducer implements IProducer {
    protected Sklad sklad;
    protected Objednavka[] material;
    protected Objednavka[] product;
    protected int buildTime;
    protected int waittTime;
    protected String produceMessage;
    protected int[] limit;
    protected Thread thread;
    protected int count;
    protected String name;
    protected Runnable onFinish;

    public GenericProducer(Sklad sklad, Objednavka[] material, Objednavka[] product, int buildTime, int waittTime, String produceMessage, int[] limit, String name,  Runnable onFinish) {
        this.sklad = sklad;
        this.material = material;
        this.product = product;
        this.buildTime = buildTime;
        this.waittTime = waittTime;
        this.produceMessage = produceMessage;
        if (product.length != limit.length) {
            throw new IllegalArgumentException("Product and limit must have same length");
        }
        this.limit = limit;
        this.name = name;

        this.onFinish = onFinish;

        count = 0;
        thread =  new Thread(this::vyrobit);
        thread.setName(name);
    }

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
                    sklad.add(product);
                    count++;
                    Logger.println(Thread.currentThread().getName(), produceMessage+": " + product[0].getItem() + " (celkem=" + (product[0].getAmount() * count) + ")");
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

    @Override
    public void stats() {
        StringBuilder sb = new StringBuilder();
        for (Objednavka p : product) {
            sb.append(p.getItem()).append(": ").append(p.getAmount() * count).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        Logger.println(Thread.currentThread().getName(), sb.toString());
    }

    public void start() {
        stop();

        thread = new Thread(this::vyrobit);
        thread.setName(name);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public void join() throws InterruptedException {
        thread.join();
    }
}
