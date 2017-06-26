package com.devcolibri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthorizationService {
    private Socket socket;
    private BufferedReader bf;
    private PrintWriter pw;
    private String nickname;
    private boolean userAvailable = false;

    public AuthorizationService(Socket socket) throws IOException {
        this.socket = socket;
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
    }

    public User authorize() {
        User user = null;
        pw.println("Welcome to chat, please login or register");
        while (!userAvailable) {
            pw.println("Enter your login (nickname)");
            nickname = getMessage();
/*            if (nickname == null || nickname.equals("/exit")) {

            }*/
            if (UserHandler.getInstance().getMap().get(nickname) != null) {
                login();
                user = UserHandler.getInstance().getMap().get(nickname);
            } else {
                user = registration();
            }
        }
        return user;
    }

    private User registration() {
        User user;
        pw.println("Choose your password");
        String password = getMessage();
        pw.println("Confirm password");
        String confirm = getMessage();
        if (password.equals(confirm)) {
            UserHandler.getInstance().getMap().put(nickname, user = new User(nickname, password));
            pw.println("Registration success");
            System.out.println(nickname + " is connected");
            PrintWriterHandler.getInstance().getMap().put(nickname, pw);
            userAvailable = true;
            return user;
        } else {
            pw.println("Registration failed, please try again");
            return null;
        }
    }

    private void login() {
        if (PrintWriterHandler.getInstance().getMap().get(nickname) != null) {
            pw.println("You're already logged in");
        } else {
            pw.println("Enter your password");
            if (UserHandler.getInstance().getMap().get(nickname).getPassword().equals(getMessage())) {
                pw.println("Login success");
                System.out.println(nickname + " is connected");
                PrintWriterHandler.getInstance().getMap().put(nickname, pw);
                userAvailable = true;
            } else {
                pw.println("Login failed, please try again");
            }
        }
    }

    private String getMessage() {
        String message = "";
        try {
            message = bf.readLine();
        } catch (IOException e) {
            //exit
        }
        return message;
    }
}
