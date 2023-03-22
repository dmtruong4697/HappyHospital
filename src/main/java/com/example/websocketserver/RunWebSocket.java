package com.example.websocketserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;

public class RunWebSocket {
    public ServerSocket server;
    public RunWebSocket () {
        try {
            server = new ServerSocket(55555);
            while (true) {
                try {
                    Socket socket = server.accept();
                    SocketHandler socketHandler = new SocketHandler(socket);
                    socketHandler.start();
                } catch (Exception e) {
                    //TODO: handle exception
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}