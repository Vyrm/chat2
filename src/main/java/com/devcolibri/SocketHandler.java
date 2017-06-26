package com.devcolibri;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketHandler {
    private static volatile SocketHandler socketHandler;
    private static Map<String, Socket> sockets;

    private SocketHandler() {
        sockets = new HashMap<String, Socket>();
    }

    public static SocketHandler getInstance() {
        if (socketHandler == null) {
            synchronized (SocketHandler.class) {
                if (socketHandler == null) {
                    socketHandler = new SocketHandler();
                }
            }
        }

        return socketHandler;
    }

    public Map<String, Socket> getMap() {
        return sockets;
    }
}

