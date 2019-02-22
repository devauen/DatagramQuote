package com.proartz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class QuoteServerThread extends Thread{
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;
    protected int portNumber = 4445;

    public QuoteServerThread() throws IOException {
        this("QuoteServer");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(portNumber);
        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch(FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }

    }

    public void run() {
        while(moreQuotes) {
            try{
                // receiving the datagram
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.format("Server started on %d port.%n" +
                        "Waiting for the requests...%n", portNumber);
                socket.receive(packet);

                // preparing the data response
                String dString = null;
                if(in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();

                // sending the response
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.format("Received the request from %s:%d%n", address, port);

                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                System.out.println("Datagram packet sent...");
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occured in server.";
        }
        return returnValue;
    }
}
