package VVS.httpserver;

import VVS.httpserver.core.ServerListenerThread;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void main(String[] args) {

        System.out.println("Server starting...");

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(3000, "/c/test");
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
