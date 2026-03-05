package com.example.drony.vyroba;

public class Vyrobce {
    Sklad sklad;
    Objednavka[] material;
    Objednavka[] product;
    int buildTime;
    int waittTime;
    Thread thread;

    public Vyrobce(Sklad sklad, Objednavka[] material, Objednavka[] product, int buildTime, int waittTime, String name) {
        this.sklad = sklad;
        this.material = material;
        this.product = product;
        this.buildTime = buildTime;
        this.waittTime = waittTime;
        thread =  new Thread(this::vyrobit);
        thread.setName(name);
    }

    public void vyrobit() {
        while (!Thread.interrupted()) {
                System.out.println("Vyrabim: "+product[0].getItem());
                if (sklad.take(material)) {
                    try {
                        Thread.sleep(buildTime);
                    } catch (InterruptedException e) {
                        sklad.add(material);
                        return;
                    }
                    sklad.add(product);
                } else {
                    System.out.println("Čeká na komponenty");
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
