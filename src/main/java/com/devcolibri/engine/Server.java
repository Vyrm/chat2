package com.devcolibri.engine;

import com.devcolibri.service.AuthorizationService;
import com.devcolibri.service.MessageService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    public void run() throws IOException {

        System.out.println("Server is working!");
        serverSocket = new ServerSocket(8081);
        AuthorizationService authorizationService = new AuthorizationService();
        MessageService messageService = new MessageService();
        while (true) {
            socket = serverSocket.accept();
            Worker worker = new Worker(socket, authorizationService, messageService);
            new Thread(worker).start();
        }
    }
}
