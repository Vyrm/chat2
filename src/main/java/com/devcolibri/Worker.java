package com.devcolibri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class Worker implements Runnable {
    private Socket socket;
    private BufferedReader bf;
    private PrintWriter pw;
    private boolean userAvailable;

    public Worker(Socket sck) throws IOException {
        this.socket = sck;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
        userAvailable = false;
    }

    public void run() {
        pw.println("Welcome to chat, enter your nickname!");
        while (!userAvailable) {
            String nickname = getMessage();
            if (nickname == null || nickname.equals("/exit")) {
                exit();
            } else if (UserHandler.getInstance().getMap().isEmpty()) {
                UserHandler.getInstance().getMap().put(nickname, new User(socket, nickname));
                userAvailable = true;
            } else {
                for (String a : UserHandler.getInstance().getMap().keySet()) {
                    if (a.equals(nickname)) {
                        pw.println("This user is already exist, choose another");
                    } else {
                        UserHandler.getInstance().getMap().put(nickname, new User(socket, nickname));
                        userAvailable = true;
                    }
                }
            }
        }
        while (userAvailable) {
            String msg = getMessage();
            if (msg == null || msg.equals("/exit")) {
                exit();
            } else {
                System.out.println(msg);
            }
        }
    }

    private String getMessage() {
        String message = "";
        try {
            message = bf.readLine();
        } catch (IOException e) {
            exit();
        }
        return message;
    }


    private void exit() {
        System.out.println("The client left the chat");
        userAvailable = false;
        try {
            socket.close();
            bf.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
