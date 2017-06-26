package com.devcolibri;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;
    private BufferedReader bf;
    private PrintWriter pw;
    private boolean userAvailable;
    private User user;
    private AuthorizationService as;

    public Worker(Socket sck) throws IOException {
        this.socket = sck;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
        userAvailable = true;
        as = new AuthorizationService(socket);
    }

    public void run() {
        user = as.authorize();

        while (userAvailable) {
            String msg = getMessage();
            if (msg == null || msg.equals("/exit")) {
                exit();
            } else {
                sendMessageToAll(msg);
                System.out.println(user.getNickname() + ": " + msg);
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

    private void sendMessageToAll(String msg) {
        for (String nickname : PrintWriterHandler.getInstance().getMap().keySet()) {
            if (!nickname.equals(user.getNickname())) {
                PrintWriterHandler.getInstance().getMap().get(nickname).println(user.getNickname() + ": " + msg);
            }
        }
    }


    private void exit() {
        System.out.println(user.getNickname() + " left the chat");
        if (!user.getNickname().equals("/exit")) {
            PrintWriterHandler.getInstance().getMap().remove(user.getNickname());
        }
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