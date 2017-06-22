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
    private boolean userAvailableExit;

    public Worker(Socket sck) throws IOException {
        this.socket = sck;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
        userAvailableExit = true;
    }

    public void run() {
        pw.println("Welcome to chat, enter your nickname!");
        getNickname();
        sendMessage();
    }

    private void getNickname() {
        boolean userAvailable = true;
        while (userAvailable && userAvailableExit) {
            try {
                String nickname = bf.readLine();
                if (nickname == null || nickname.equals("/exit")) {
                    userAvailable = exit();
                } else if (UserHandler.getInstance().getMap().isEmpty()) {
                    UserHandler.getInstance().getMap().put(socket, new User(nickname));
                    userAvailable = false;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        boolean userAvailable = true;
        while (userAvailable && userAvailableExit) {
            try {
                String msg = bf.readLine();
                if (msg == null || msg.equals("/exit")) {
                    userAvailable = exit();
                } else {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean exit() throws IOException {
        System.out.println("The client left the chat");
        userAvailableExit = false;
        socket.close();
        bf.close();
        pw.close();
        return false;
    }
}
