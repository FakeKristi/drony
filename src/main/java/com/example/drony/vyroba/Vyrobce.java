package com.example.drony.vyroba;

public class Vyrobce {
    Sklad sklad;
    Objednavka[] material;
    Objednavka[] product;
    int buildTime;
    int waittTime;
    Thread thread;

    public Vyrobce(Sklad sklad, Objednavka[] material, Objednavka[] product, int buildTime, int waittTime) {
        this.sklad = sklad;
        this.material = material;
        this.product = product;
        this.buildTime = buildTime;
        this.waittTime = waittTime;
        thread =  new Thread(this::vyrobit);
    }

    public void vyrobit() {
        while (!Thread.interrupted()) {
            try {
                if (sklad.take(material)) {
                    Thread.sleep(buildTime);
                    sklad.add(product);
                } else {
                    Thread.sleep(waittTime);
                }
            } catch (InterruptedException e) {
                sklad.add(material);
                throw new RuntimeException(e);
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
