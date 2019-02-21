package com.proartz;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class QuoteClient {

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            System.exit(1);
        }
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            System.out.format("Starting QuoteClientThread.%n");
            new QuoteClientThread(address).start();
        } catch (UnknownHostException e) {
            System.err.format("Unknown host: %s", args[0]);
        }
    }
}
