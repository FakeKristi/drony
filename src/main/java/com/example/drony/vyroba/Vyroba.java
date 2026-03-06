package com.example.drony.vyroba;

import com.example.drony.vyroba.producers.GenericProducer;
import com.example.drony.vyroba.producers.IProducer;

import java.util.ArrayList;

public class Vyroba {

    private Sklad sklad;
    private ArrayList<IProducer> producers = new ArrayList<>();
    private boolean stopped;

    public Vyroba(Sklad sklad, int requirement) {
        this.sklad = sklad;

        sklad.add(new Objednavka[]{
                new Objednavka("Hliník", 10000),
                new Objednavka("Plast", 10000),
                new Objednavka("Čip", 1000),
        });


        IProducer genericProducerRamu = new GenericProducer(
                sklad,
                new Objednavka[]{
                        new Objednavka("Hliník", 60)
                },
                new Objednavka[]{
                        new Objednavka("Rám", 1)
                },
                1000,
                1000,
                "vytvořil",
                new int[]{30},
                "VYROBCE-RAM",
                () -> {}
        );

        IProducer genericProducerVrtuli = new GenericProducer(
                sklad,
                new Objednavka[]{
                        new Objednavka("Plast", 30)
                },
                new Objednavka[]{
                        new Objednavka("Sada vrtulí", 1)
                },
                1000,
                1000,
                "vytvořil",
                new int[]{30},
                "VYROBCE-VRTULE",
                () -> {}
        );

        IProducer genericProducerRidiciDesky = new GenericProducer(
                sklad,
                new Objednavka[]{
                        new Objednavka("Čip", 2),
                        new Objednavka("Hliník", 10),
                        new Objednavka("Plast", 5)
                },
                new Objednavka[]{
                        new Objednavka("Řídicí deska", 1)
                },
                2000,
                1000,
                "vytvořil",
                new int[]{30},
                "VYROBCE-DESKA",
                () -> {}
        );

        IProducer sestavitelDronu = new GenericProducer(
                sklad,
                new Objednavka[]{
                        new Objednavka("Rám", 1),
                        new Objednavka("Sada vrtulí", 1),
                        new Objednavka("Řídicí deska", 1)
                },
                new Objednavka[]{
                        new Objednavka("Droní kit", 1)
                },
                1000,
                1000,
                "sestavil",
                new int[]{requirement},
                "SESTAVITEL-DRONU-1",
                this::finalStop
        );

        IProducer sestavitelDronu2 = new GenericProducer(
                sklad,
                new Objednavka[]{
                        new Objednavka("Rám", 1),
                        new Objednavka("Sada vrtulí", 1),
                        new Objednavka("Řídicí deska", 1)
                },
                new Objednavka[]{
                        new Objednavka("Droní kit", 1)
                },
                1000,
                1000,
                "sestavil",
                new int[]{requirement},
                "SESTAVITEL-DRONU-2",
                this::finalStop
        );

        IProducer skladnik = new RandomProducer(
                sklad,
                new Objednavka[]{},
                new Objednavka[]{
                        new Objednavka("Hliník", 100),
                        new Objednavka("Plast", 100),
                        new Objednavka("Čip", 100)
                },
                1000,
                1000,
                "doplnil",
                new int[]{1000000,1000000,1000000},
                "Skladnik",
                () -> {}
        );

        producers.add(genericProducerRamu);
        producers.add(genericProducerVrtuli);
        producers.add(genericProducerRidiciDesky);
        producers.add(sestavitelDronu);
        producers.add(sestavitelDronu2);
        producers.add(skladnik);
    }

    public void start() {
        if (stopped) {
            return;
        }
        for (IProducer genericProducer : producers) {
            genericProducer.start();
        }
    }

    public void stop() {
        if (stopped) {
            return;
        }
        for (IProducer genericProducer : producers) {
            genericProducer.stop();
        }
    }

    public synchronized void finalStop() {
        if (stopped) {
            return;
        }

        stop();
        stopped = true;

        StringBuilder sb = new StringBuilder("Statistics:\n");

        for (IProducer producer : producers) {
            sb.append(producer.stats()).append("\n");

        }
        Logger.println("SYSTEM", sb.toString());
    }
}
