package com.example.drony.vyroba;

public class Logger {
    public synchronized static void println(String from, String message) {
        System.out.println(from+" "+message);
    }
}
