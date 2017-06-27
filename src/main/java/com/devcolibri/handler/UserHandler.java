package com.devcolibri.handler;

import com.devcolibri.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    private static Map<String, User> clients;

    public UserHandler() {
        clients = new HashMap<>();
    }

    public static Map<String, User> getMap() {
        return clients;
    }
}