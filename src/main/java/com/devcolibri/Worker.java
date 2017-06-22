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
        userAvailable = true;
    }

    public void run() {
        pw.println("Welcome to chat, enter your nickname!");
        while (userAvailable) {
            String nickname = getMessage();
            if (nickname == null || nickname.equals("/exit")) {
                exit();
            }
            if (UserHandler.getInstance().getMap().isEmpty()) {
                UserHandler.getInstance().getMap().put(socket, new User(nickname));
            } else {
                for (Map.Entry<Socket, User> entry : UserHandler.getInstance().getMap().entrySet()) {
                    if (entry.getValue().equals(new User(nickname))) { //TODO
                        pw.println("This user is already exist, choose another");
                    } else {
                        UserHandler.getInstance().getMap().put(socket, new User(nickname));
                        userAvailable = false;
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
            e.printStackTrace();
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
