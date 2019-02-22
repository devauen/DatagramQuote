package com.proartz;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastClientThread extends Thread {

    protected int portNumber = 4446;
    protected String groupName = "224.0.0.1";

    public MulticastClientThread() {
        this("MulticastClientThread");
    }

    public MulticastClientThread(String name) {
        super(name);
    }

    public void run() {

        try {
            MulticastSocket socket = new MulticastSocket(portNumber);
            InetAddress group = InetAddress.getByName(groupName);
            socket.joinGroup(group);

            DatagramPacket packet;
            for(int i = 0; i < 5; i++) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                System.out.format("Waiting for the packets multicasted to %s:%d...%n", groupName, portNumber);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int packetPort = packet.getPort();

                String received = new String(packet.getData());
                System.out.format("Packet received from %s:%d%n", address, packetPort);
                System.out.println("Quote of the Moment: " + received);
                System.out.println();
            }

            socket.leaveGroup(group);
            socket.close();
        } catch (UnknownHostException e) {
            System.err.format("Can't find the %s host%n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
