/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights.old;

import com.oliveratkinson.audiolights.Wifi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author olive
 */
@Deprecated
public class Network {

    private DatagramSocket dgramSocket;
    public boolean running;
    private byte[] buf = new byte[128];
    private final int port;

    public Network(int port) {
        this.port = port;
        try {
            //
            dgramSocket = new DatagramSocket(port);

            //
        } catch (SocketException ex) {
            Logger.getLogger(Wifi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void client() {
        running = true;

        while (running) {

            InetAddress ipAddress;
            try {
                ipAddress = InetAddress.getByName("192.168.4.1");
                Socket socket = new Socket(ipAddress, port);
                while (true) {
                    System.out.print("> ");

                    // wrap in PrintWriter so you can use normal text format
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // true for autoflush
                    // get user input
                    Scanner sc = new Scanner(System.in);
                    // write user input out on the socket
                    String s = sc.nextLine();
                    writer.println(s);

                    System.out.println(s);

                    // buffered reader allows for reading by lines
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();
                    System.out.println("response > " + line);

                }
            } catch (IOException ex) {
                Logger.getLogger(Wifi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // do not use yet
//    public void server() {
//        running = true;
//
//        while (running) {
//            DatagramPacket packet = new DatagramPacket(buf, buf.length);
//
//            try {
//                socket.receive(packet);
//                // save ip and port for later
//                InetAddress address = packet.getAddress();
//                int clientPort = packet.getPort();
//                // transfer packet contents into a string
//                String received = new String(packet.getData(), 0, packet.getLength());
//
//                System.out.println(received);
//
//                // end... what did you expect?
//                if (received.equals("end")) {
//                    running = false;
//                    continue;
//                }
//                // create packet contents
//                if (received.equals("feed me")) {
//                    for (byte b : buf) {
//                        buf[b] = 0111;
//                    }
//
//                }
//
//                // generate a packet to send
//                packet = new DatagramPacket(buf, buf.length, address, clientPort);
//
//                socket.send(packet);
//
//            } catch (IOException ex) {
//                Logger.getLogger(Wifi.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        socket.close();
//    }
}
