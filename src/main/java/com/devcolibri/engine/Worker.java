package com.devcolibri.engine;

import com.devcolibri.domain.User;
import com.devcolibri.exception.UserDisconnectedException;
import com.devcolibri.handler.PrintWriterHandler;
import com.devcolibri.service.AuthorizationService;
import com.devcolibri.service.MessageService;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;
    private MessageService messageService;
    private AuthorizationService authorizationService;

    public Worker(Socket socket, AuthorizationService authorizationService, MessageService messageService) {
        this.socket = socket;
        this.messageService = messageService;
        this.authorizationService = authorizationService;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            User user = authorizationService.authorize(bufferedReader, printWriter);
            String message = bufferedReader.readLine();
            while (message != null && !message.equals("/exit")) {
                messageService.send(message, user);
                System.out.println(user.getNickname() + ": " + message);
                message = bufferedReader.readLine();
            }
            exit(user, bufferedReader, printWriter);
        } catch (IOException | UserDisconnectedException e) {
            // TODO: 26.06.2017
        }

    }

    private void exit(User user, BufferedReader bufferedReader, PrintWriter printWriter) {
        System.out.println(user.getNickname() + " left the chat");
        if (!user.getNickname().equals("/exit")) {
            PrintWriterHandler.getInstance().getMap().remove(user.getNickname());
        }
        try {
            socket.close();
            bufferedReader.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}