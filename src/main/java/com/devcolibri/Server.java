package com.devcolibri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;

    public void run(){
        try {
            System.out.println("Server is working");
            serverSocket = new ServerSocket(8081);
            while (true) {
                socket = serverSocket.accept();
                Worker worker = new Worker(socket);
                new Thread(worker).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
