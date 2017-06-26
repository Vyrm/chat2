package com.devcolibri.handler;

import com.devcolibri.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {
    private static volatile UserHandler userHandler;
    private static Map<String, User> clients;

    private UserHandler() {
        clients = new HashMap<>();
    }

    public static UserHandler getInstance() {
        if (userHandler == null) {
            synchronized (UserHandler.class) {
                if (userHandler == null) {
                    userHandler = new UserHandler();
                }
            }
        }

        return userHandler;
    }

    public Map<String, User> getMap() {
        return clients;
    }
}