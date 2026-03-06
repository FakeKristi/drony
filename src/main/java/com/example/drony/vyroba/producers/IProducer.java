package com.example.drony.vyroba.producers;

public interface IProducer {
    public void vyrobit();
    public void stats();
    public void start();
    public void stop();
    public void join() throws InterruptedException;
}
