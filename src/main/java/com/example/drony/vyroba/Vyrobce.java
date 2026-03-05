package com.example.drony.vyroba;

public class Vyrobce {
    Sklad sklad;
    Objednavka[] material;
    Objednavka[] product;
    int buildTime;
    int waittTime;
    int[] limit;
    Thread thread;
    int count;

    public Vyrobce(Sklad sklad, Objednavka[] material, Objednavka[] product, int buildTime, int waittTime, int[] limit, String name) {
        this.sklad = sklad;
        this.material = material;
        this.product = product;
        this.buildTime = buildTime;
        this.waittTime = waittTime;
        if (product.length != limit.length) {
            throw new IllegalArgumentException("Product and limit must have same length");
        }
        this.limit = limit;

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
                    Logger.println(Thread.currentThread().getName(), "vyrobil: " + product[0].getItem() + " (celkem=" + (product[0].getAmount() * count) + ")");
                } else {
                    Logger.println(Thread.currentThread().getName(), "čeká na materiál pro: " + product[0].getItem() + "");
                    try {
                        Thread.sleep(waittTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            } else {
                Logger.println(Thread.currentThread().getName(), "pozastavil výrobu: " + product[0].getItem());
                try {
                    Thread.sleep(waittTime);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public void join() throws InterruptedException {
        thread.join();
    }
}
