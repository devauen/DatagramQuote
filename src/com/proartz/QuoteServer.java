package com.proartz;

import java.io.IOException;

public class QuoteServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting QuoteServerThread...");
        new QuoteServerThread().start();
    }
}
