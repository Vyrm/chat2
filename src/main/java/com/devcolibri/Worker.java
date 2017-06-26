package com.devcolibri;

import java.io.*;
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
                if (UserHandler.getInstance().getMap().get(nickname) != null) {
                    login();
                } else {
                    registration();
                }
            }
        }
        while (userAvailable) {
            String msg = getMessage();
            if (msg == null || msg.equals("/exit")) {
                exit();
            } else {
                sendMessageToAll(msg);
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

    private void sendMessageToAll(String msg) {
        for (String nickname : SocketHandler.getInstance().getMap().keySet()) {
            if (!nickname.equals(this.nickname)) {
                try {
                    PrintWriter write = new PrintWriter(SocketHandler.
                            getInstance().getMap().get(nickname).getOutputStream(), true);
                    write.println(this.nickname + ": " + msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void registration() {
        pw.println("Choose your password");
        String password = getMessage();
        pw.println("Confirm password");
        String confirm = getMessage();
        if (password.equals(confirm)) {
            UserHandler.getInstance().getMap().put(nickname, new User(nickname, password));
            pw.println("Registration success");
            System.out.println(nickname + " is connected");
            SocketHandler.getInstance().getMap().put(nickname, socket);
            userAvailable = true;
        } else {
            pw.println("Registration failed, please try again");
        }
    }

    private void login() {
        if (SocketHandler.getInstance().getMap().get(nickname) != null) {
            pw.println("You're already logged in");
        } else {
            pw.println("Enter your password");
            if (UserHandler.getInstance().getMap().get(nickname).getPassword().equals(getMessage())) {
                pw.println("Login success");
                System.out.println(nickname + " is connected");
                SocketHandler.getInstance().getMap().put(nickname, socket);
                userAvailable = true;
            } else {
                pw.println("Login failed, please try again");
            }
        }
    }

    private void exit() {
        System.out.println(nickname + " left the chat");
        if (!nickname.equals("/exit")) {
            SocketHandler.getInstance().getMap().remove(nickname);
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