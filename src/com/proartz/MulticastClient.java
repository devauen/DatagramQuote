package com.proartz;

public class MulticastClient {
    public static void main(String[] args) {
        System.out.format("Starting QuoteClientThread.%n");
        new MulticastClientThread().start();
    }
}
