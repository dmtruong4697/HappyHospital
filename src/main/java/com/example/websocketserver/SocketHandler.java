package com.example.websocketserver;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.main.Scene;

public class SocketHandler extends Thread {
    public Socket socket;
    public Scene scene;
    public SocketHandler(Socket socket) {
        this.socket = socket;
        this.scene = new Scene();
    }
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            handshake(in, out);
            handleMessage(in, out);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    public void handshake(InputStream in, OutputStream out) throws Exception {
        String data = new Scanner(in, "UTF-8").useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^GET").matcher(data);
        if (get.find()) {
            Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
            match.find();
            byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                    + "Connection: Upgrade\r\n"
                    + "Upgrade: websocket\r\n"
                    + "Sec-WebSocket-Accept: "
                    + Base64.getEncoder().encodeToString(
                            MessageDigest
                                    .getInstance("SHA-1")
                                    .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                            .getBytes("UTF-8")))
                    + "\r\n\r\n")
                    .getBytes("UTF-8");

            out.write(response, 0, response.length);

        }
    }

    public void handleMessage(InputStream in, OutputStream out) {
        try {
            int count = 0;
            while (true) {

                byte[] buff = new byte[1024];
                int lineData = in.read(buff);
                for (int i = 0; i < lineData - 6; i++) {
                    buff[i + 6] = (byte) (buff[i % 4 + 2] ^ buff[i + 6]);
                }
                
                var kt=0;
                String mes = new String(buff, 6, lineData - 6, "UTF-8");
                if (mes.equals("ClickSaveButton")) {
                    scene.handleClickSaveButton();
                    mes = "Save";
                }
                else if (mes.equals("ClickLoadButton")) {
                    scene.handleClickLoadButton();
                    mes = "Load";
                    System.out.println("Load");
                }
                String line = scene.update(mes);
                if (kt==1) {
                    System.out.println(line);
                }
                int length = line.getBytes("UTF-8").length;
                byte[] sendHead = new byte[4];
                if (length < 126) {
                    sendHead[0] = buff[0];
                    sendHead[1] = (byte) length;
                    out.write(sendHead[0]);
                    out.write(sendHead[1]);
                } else {
                    sendHead[0] = buff[0];
                    sendHead[1] = (byte) 126;
                    sendHead[2] = (byte) (length >> 8);
                    sendHead[3] = (byte) (length & 255);
                    out.write(sendHead);
                }
                out.write(line.getBytes("UTF-8"));

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
