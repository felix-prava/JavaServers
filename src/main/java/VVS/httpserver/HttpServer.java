package VVS.httpserver;

import VVS.httpserver.core.ServerState;

public class HttpServer {

    public static void main(String[] args) {
        int state = 1;
        ServerState serverState = new ServerState(state);
        serverState.initializeOptions();

        while (serverState.getState() != -1) {
            serverState.processServerState();
        }
    }
}
