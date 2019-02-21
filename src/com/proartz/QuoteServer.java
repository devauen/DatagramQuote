package com.proartz;

public class QuoteServer {
    public static void main(String[] args) {
        new QuoteServerThread().start();
    }
}
