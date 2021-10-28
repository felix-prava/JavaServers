package Admin;

import Server.ServerListenerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerState {

    private int state;
    private int port = 3000;
    private Map<Integer, String> menu;
    private String rootDirectory = "/", maintenanceDirectory = "/";
    private ServerListenerThread serverListenerThread = null;
    private boolean serverISRunning = false, serverIsOnMaintenanceMode = false;

    public ServerState(int state) { this.state = state; }

    public int getState() { return state; }

    public void setState(int state) { this.state = state; }

    public Map<Integer, String> getMenu() { return menu; }

    public int processServerState() {
        Scanner scan = new Scanner(System.in);
        int currentState = getState();
        System.out.println("The current state of the server is: " + currentState + getMenu().get(state + 100));
        showMenu(currentState);
        System.out.print("Your option: ");
        String input = scan.nextLine();
        System.out.println();
        if (!isNumber(input)) {
            System.out.print("This is not a valid option!\n");
            return -10;
        }
        int receivedState = Integer.parseInt(input);
        if (!(receivedState >= -1 && receivedState <= 2)) {
            System.out.println("This is not a valid state\n");
            return -10;
        } else if (receivedState == currentState) {
            System.out.println("The server is already in this state\n");
            return -10;
        } else {
            setState(receivedState);
            System.out.println("The server state was changed\n");
            return receivedState;
        }
    }

    public void initializeOptions() {
        menu = new HashMap<>();
        menu.put(-1, "Close the program");
        menu.put(0, "Stop the server");
        menu.put(1, "Put the server on normal mode");
        menu.put(2, "Put the server on maintenance mode");
        menu.put(100, " and this means that the server is stopped");
        menu.put(101, " and this means that the server is in normal mode");
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
    }

    private boolean isNumber(String input) {
        try {
            int num = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("This is not an integer\n");
            return false;
        }
    }

    public int getPort() {
        return port;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public String getMaintenanceDirectory() {
        return maintenanceDirectory;
    }
}

    
