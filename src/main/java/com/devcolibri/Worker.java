package com.devcolibri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;
    private BufferedReader bf;
    private PrintWriter pw;

    public Worker(Socket sck) {
        this.socket = sck;
        try {
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean userAvailable = true;
        pw.println("Welcome to chat!");
        while (userAvailable) {
            try {
                String msg = bf.readLine();
                if (msg.equals("/exit")) {
                    System.out.println("The client left the chat");
                    userAvailable = false;
                    socket.close();
                    break;
                }
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("The client left the chat");
                userAvailable = false;
            }
        }
    }
}
