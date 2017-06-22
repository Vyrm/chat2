package com.devcolibri;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {
    private static volatile UserHandler userHandler;
    private static Map<String, User> clients;

    private UserHandler() {
        clients = new HashMap<String, User>();
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