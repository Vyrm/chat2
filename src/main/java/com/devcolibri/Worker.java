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
    private boolean userAvailable;

    public Worker(Socket sck) throws IOException {
        this.socket = sck;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        userAvailable = true;
        pw.println("Welcome to chat!");
        while (userAvailable) {
            try {
                String msg = bf.readLine();
                if (msg == null || msg.equals("/exit")) {
                    exit();
                } else {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exit() throws IOException {
        System.out.println("The client left the chat");
        userAvailable = false;
        socket.close();
        bf.close();
        pw.close();
    }
}
