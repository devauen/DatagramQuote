package com.proartz;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class MulticastServerThread extends QuoteServerThread{

    private long FIVE_SECONDS = 5000;
    String groupName = "224.0.0.1";

    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
    }

    public void run() {
        portNumber = 4446;
        moreQuotes = true;

        while(moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // preparing the data response
                String dString = null;
                if(in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();

                InetAddress group = InetAddress.getByName(groupName);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portNumber);

                System.out.format("Sending quote to the group %s%n", group);
                socket.send(packet);

                try {
                    System.out.println("Sleeping...");
                    sleep(FIVE_SECONDS);
                    System.out.println();
                } catch (InterruptedException e) {}

            }catch(UnknownHostException e) {
                System.err.format("Uknowns group: %s", groupName);
            }catch(IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }
}
