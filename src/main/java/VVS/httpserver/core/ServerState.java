package VVS.httpserver.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerState {

    private int state;
    private Map<Integer, String> menu;

    public ServerState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Map<Integer, String> getMenu() {
        return menu;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static void main(String[] args) {
        System.out.println("Server starting...");

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(3000, "/c/test");
            serverListenerThread.test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processServerState() {
        Scanner scan = new Scanner(System.in);
        int currentState = getState();
        System.out.print("The current state of the server is: " + currentState + getMenu().get(state + 100));
        showMenu(currentState);
        System.out.println("Your option:");
        String input = scan.nextLine();
        int receivedState = Integer.parseInt(input);
        if (!(receivedState >= -1 && receivedState <= 2)) {
            System.out.println("This is not a valid state\n");
        } else if (receivedState == currentState) {
            System.out.println("The server is already in this state\n");
        } else {
            setState(receivedState);
            startState(receivedState);
            System.out.println("The server state was changed\n");
        }
    }

    private void startState(int receivedState) {
        if (receivedState == -1) {
            stopProgram();
        } else if (receivedState == 0) {
            closeServer();
        } else if (receivedState == 1) {
            openServer();
        } else {
            setServerOnMaintenanceMode();
        }
    }

    private void setServerOnMaintenanceMode() {
    }

    private void openServer() {
    }

    private void closeServer() {
    }

    private void stopProgram() {
    }

    public void initializeOptions() {
        menu = new HashMap<>();
        menu.put(-1, "Close the program");
        menu.put(0, "Stop the server");
        menu.put(1, "Open the server");
        menu.put(2, "Put the server on maintenance mode");
        menu.put(100, " and this means that the server is stopped");
        menu.put(101, " and this means that the server is open");
        menu.put(102, " and this means that the server is in maintenance mode");
    }

    public void showMenu(int currentState) {
        System.out.println("You can choose one of the following:");
        for (int i = -1; i < 3; i++) {
            if (i == currentState) {
                continue;
            }
            System.out.println(i + " - " + getMenu().get(i));
        }
        System.out.println();
    }
}

    
