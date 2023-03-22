package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.httpserver.RunHttpServer;
import com.example.main.Scene;
import com.example.websocketserver.RunWebSocket;

public class App {

    public static void main(String[] args) throws Exception {
        RunHttpServer httpServer = new RunHttpServer();
        httpServer.start();
        RunWebSocket webSocket = new RunWebSocket();
        
    }
}

