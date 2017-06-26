package com.devcolibri.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.devcolibri.handler.PrintWriterHandler;
import com.devcolibri.domain.User;
import com.devcolibri.exception.UserDisconnectedException;
import com.devcolibri.handler.UserHandler;

public class AuthorizationService {

    public User authorize(BufferedReader bufferedReader, PrintWriter printWriter) throws IOException, UserDisconnectedException{
        User user = null;
        printWriter.println("Welcome to chat, please login or register");
        while (user == null) {
            printWriter.println("Enter your login (nickname)");
            String nickname = bufferedReader.readLine();
            if (nickname != null && !nickname.equals("/exit")) {
                user = UserHandler.getInstance().getMap().containsKey(nickname) ? login(nickname, bufferedReader, printWriter) : registration(nickname, bufferedReader, printWriter);
            } else {
                throw new UserDisconnectedException();
            }     
        }
        return user;
    }

    private User registration(String nickname, BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
        printWriter.println("Choose your password");
        String password = bufferedReader.readLine();
        printWriter.println("Confirm password");
        String confirm = bufferedReader.readLine();
        if (password.equals(confirm)) {
            User user = new User(nickname, password);
            UserHandler.getInstance().getMap().put(nickname, user);
            printWriter.println("Registration success");
            System.out.println(nickname + " is connected");
            PrintWriterHandler.getInstance().getMap().put(nickname, printWriter);
            return user;
        } else {
            printWriter.println("Registration failed, please try again");
            return null;
        }
    }

    private User login(String nickname, BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
        User user = null;
        if (!PrintWriterHandler.getInstance().getMap().containsKey(nickname)) {
            printWriter.println("Enter your password");
            user = UserHandler.getInstance().getMap().get(nickname);
            if (user != null && user.getPassword().equals(bufferedReader.readLine())) {
                printWriter.println("Login success");
                System.out.println(nickname + " is connected");
                PrintWriterHandler.getInstance().getMap().put(nickname, printWriter);
            } else {
                printWriter.println("Login failed, please try again");
            }
        } else {
            printWriter.println("You're already logged in");
        }
        return user;
    }
}