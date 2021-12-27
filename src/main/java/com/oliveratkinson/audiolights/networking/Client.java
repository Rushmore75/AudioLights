/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights.networking;

import com.oliveratkinson.audiolights.Wifi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author olive
 */
public class Client {

    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;

    public Client(String ip, int port) throws UnknownHostException, IOException {
        // "192.168.4.1"

        this.socket = new Socket(ip, port);
        // wrap in PrintWriter so you can use normal text format
        this.writer = new PrintWriter(socket.getOutputStream(), true); // true for autoflush
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void callAndResponse() {
        boolean running = true;
        String rply;
        
        while (running) {
            rply = getReply();
            System.out.println(rply);
            if (rply.startsWith("send:")) send(getUserInput());
            
        }
    }

    public void send(String msg) {
        writer.println(msg);
    }

    private String getUserInput() {
        // get user input
        Scanner sc = new Scanner(System.in);
        // get next user input line
        return sc.nextLine();
    }

    private String getReply() {
        try {
            // this should block
            return reader.readLine();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
