package com.example.drony.vyroba;

import java.util.ArrayList;

public class Vyroba {

    private Sklad sklad;
    private ArrayList<Vyrobce> vyrobci = new ArrayList<>();

    public Vyroba(Sklad sklad) {
        this.sklad = sklad;

        sklad.add(new Objednavka[]{
                new Objednavka("Hliník", 10000),
                new Objednavka("Plast", 10000),
                new Objednavka("Čip", 1000),
        });



        Vyrobce vyrobceRamu = new Vyrobce(
                sklad,
                new Objednavka[]{
                        new Objednavka("Hliník", 60)
                },
                new Objednavka[]{
                        new Objednavka("Rám", 1)
                },
                1000,
                1000,
                "VYROBCE-RAM"
        );

        Vyrobce vyrobceVrtuli = new Vyrobce(
                sklad,
                new Objednavka[]{
                        new Objednavka("Plast", 30)
                },
                new Objednavka[]{
                        new Objednavka("Sada vrtulí", 1)
                },
                1000,
                1000,
                "VYROBCE-VRTULE"
        );

        Vyrobce vyrobceRidiciDesky = new Vyrobce(
                sklad,
                new Objednavka[]{
                        new Objednavka("Čip", 2),
                        new Objednavka("Hliník", 10)
                },
                new Objednavka[]{
                        new Objednavka("Řídicí deska", 1)
                },
                1000,
                1000,
                "VYROBCE-DESKA"
        );

        Vyrobce sestavitelDronu = new Vyrobce(
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
                "SESTAVITEL-DRONU-1"
        );

        Vyrobce sestavitelDronu2 = new Vyrobce(
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
                "SESTAVITEL-DRONU-2"
        );

        vyrobci.add(vyrobceRamu);
        vyrobci.add(vyrobceVrtuli);
        vyrobci.add(vyrobceRidiciDesky);
        vyrobci.add(sestavitelDronu);
        vyrobci.add(sestavitelDronu2);
    }

    public void start() {
        for (Vyrobce vyrobce : vyrobci) {
            vyrobce.start();
        }
    }

    public void stop() {
        for (Vyrobce vyrobce : vyrobci) {
            vyrobce.stop();
        }
    }
}
