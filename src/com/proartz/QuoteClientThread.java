package com.proartz;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class QuoteClientThread extends Thread{
    protected DatagramSocket socket = null;
    byte[] buf = null;
    InetAddress address;
    int portNumber = 4445;

    public QuoteClientThread(InetAddress address) throws IOException{
        this(address,"QuoteClientThread");
    }

    public QuoteClientThread(InetAddress address, String name) throws IOException {
        this.address = address;
        this.buf = new byte[256];
        socket = new DatagramSocket();
    }

    public void run() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portNumber);
            socket.send(packet);
            System.out.format("Sending request packet to %s:%d%n", address, portNumber);

            // get response
            packet = new DatagramPacket(buf, buf.length);
            System.out.println("Waiting for the respons...");
            socket.receive(packet);

            // display response
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Quote of the Moment: " + received);

            socket.close();

        } catch(IOException e) {}
    }
}
