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
    private String nickname;

    public Worker(Socket sck) throws IOException {
        this.socket = sck;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
        userAvailable = false;
    }

    public void run() {
        pw.println("Welcome to chat, please login or register");
        while (!userAvailable) {
            pw.println("Enter your login (nickname)");
            nickname = getMessage();
            if (nickname == null || nickname.equals("/exit")) {
                exit();
            } else {
                if (UserHandler.getInstance().getMap().keySet().contains(nickname)) {
                    if (UserHandler.getInstance().getMap().get(nickname).isLoggined()) {
                        pw.println("You're already logged in");
                    } else {
                        pw.println("Enter your password");
                        if (UserHandler.getInstance().getMap().get(nickname).getPassword().equals(getMessage())) {
                            pw.println("Login success");
                            UserHandler.getInstance().getMap().get(nickname).setLoggined(true);
                            userAvailable = true;
                        } else {
                            pw.println("Login failed, please try again");
                        }
                    }
                } else {
                    pw.println("Choose your password");
                    String password = getMessage();
                    pw.println("Confirm password");
                    String confirm = getMessage();
                    if (password.equals(confirm)) {
                        UserHandler.getInstance().getMap().put(nickname, new User(socket, nickname, password));
                        pw.println("Registration success");
                        UserHandler.getInstance().getMap().get(nickname).setLoggined(true);
                        userAvailable = true;
                    } else {
                        pw.println("Registration failed, please try again");
                    }
                }
            }
        }
        while (userAvailable) {
            String msg = getMessage();
            if (msg == null || msg.equals("/exit")) {
                exit();
            } else {
                System.out.println(nickname + ": " + msg);
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
        if (!nickname.equals("/exit")) {
            UserHandler.getInstance().getMap().get(nickname).setLoggined(false);
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
