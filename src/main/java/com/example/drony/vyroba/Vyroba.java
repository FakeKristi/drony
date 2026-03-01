package com.example.drony.vyroba;

public class Vyroba {
    public static void main() {
        Sklad sklad = new Sklad();

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
                1000
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
                1000
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
                1000
        );

        Vyrobce sestavitelDronu = new Vyrobce(
                sklad,
                new Objednavka[]{
                        new Objednavka("Rám", 1),
                        new Objednavka("Sada  vrtulí", 1),
                        new Objednavka("Řídicí deska", 1)
                },
                new Objednavka[]{
                        new Objednavka("Droní kit", 1)
                },
                1000,
                1000
        );


    }
}
