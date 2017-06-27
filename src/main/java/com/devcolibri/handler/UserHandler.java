package com.devcolibri.handler;

import com.devcolibri.domain.User;

import java.util.concurrent.ConcurrentHashMap;

public class UserHandler {

    private ConcurrentHashMap<String, User> clients;

    public UserHandler() {
        clients = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, User> getMap() {
        return clients;
    }
}