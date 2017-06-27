package com.devcolibri.service;

import com.devcolibri.handler.PrintWriterHandler;
import com.devcolibri.domain.User;

public class MessageService {
    private PrintWriterHandler printWriterHandler;

    public MessageService(PrintWriterHandler printWriterHandler) {
        this.printWriterHandler = printWriterHandler;
    }

    public void send(String message, User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + ": " + message);
            }
        }
    }

    public void sendUserLogin(User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + " is connected.");
            }
        }
    }

    public void sendUserExit(User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + " left the chat.");
                printWriterHandler.getMap().remove(excludedUser.getNickname());
            }
        }
    }

}
