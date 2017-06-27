package com.devcolibri.service;

import com.devcolibri.handler.PrintWriterHandler;
import com.devcolibri.domain.User;

public class MessageService {

    public void send(String message, User excludedUser) {
        for (String nickname : PrintWriterHandler.getInstance().getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                PrintWriterHandler.getInstance().getMap().get(nickname).println(excludedUser.getNickname()
                        + ": " + message);
            }
        }
    }

    public void sendUserLogin(User excludedUser) {
        for (String nickname : PrintWriterHandler.getInstance().getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                PrintWriterHandler.getInstance().getMap().get(nickname).println(excludedUser.getNickname() + " is connected.");
            }
        }
    }

    public void sendUserExit(User excludedUser) {
        for (String nickname : PrintWriterHandler.getInstance().getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                PrintWriterHandler.getInstance().getMap().get(nickname).println(excludedUser.getNickname() + " left the chat.");
            }
        }
    }

}
