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
        if (!(receivedState >= -1 && receivedState <= 5)) {
            System.out.println("This is not a valid state\n");
            return -10;
        } else if (receivedState == currentState) {
            System.out.println("The server is already in this state\n");
            return -10;
        } else if (receivedState > 2 && receivedState < 6) {
            if (!serverISRunning) {
                if (receivedState == 3) {
                    System.out.print("Type the value of the new port: ");
                    String portInput = scan.nextLine();
                    System.out.println();
                    if (!isNumber(portInput)) {
                        System.out.print("This is not a valid value for the port!\n");
                        return -10;
                    }
                    this.port = Integer.parseInt(portInput);
                    System.out.println("The new port is: " + this.port + "\n");
                    return 3;
                } else if (receivedState == 4) {
                    System.out.print("Type the value of the new root directory: ");
                    String newRootDirectory = scan.nextLine();
                    System.out.println();
                    this.rootDirectory = newRootDirectory;
                    System.out.println("The new root directory is: " + this.rootDirectory + "\n");
                    return 4;
                } else {
                    System.out.print("Type the value of the new maintenance directory: ");
                    String newMaintenanceDirectory = scan.nextLine();
                    System.out.println();
                    this.maintenanceDirectory = newMaintenanceDirectory;
                    System.out.println("The new maintenance directory is: " + this.maintenanceDirectory + "\n");
                    return 5;
                }
            } else {
                if (currentState == 1) {
                    if (receivedState == 5) {
                        System.out.print("Type the value of the new maintenance directory: ");
                        String newMaintenanceDirectory = scan.nextLine();
                        System.out.println();
                        this.maintenanceDirectory = newMaintenanceDirectory;
                        System.out.println("The new maintenance directory is: " + this.maintenanceDirectory + "\n");
                        return 5;
                    }
                    System.out.println("This is not a valid state\n");
                }
                if (currentState == 2) {
                    if (receivedState == 4) {
                        System.out.print("Type the value of the new root directory: ");
                        String newRootDirectory = scan.nextLine();
                        System.out.println();
                        this.rootDirectory = newRootDirectory;
                        System.out.println("The new root directory is: " + this.rootDirectory + "\n");
                        return 4;
                    }
                    System.out.println("This is not a valid state\n");
                }
                return -10;
            }
        } else {
            setState(receivedState);
            serverISRunning = (receivedState > 0);
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
        menu.put(3, "Change the port of the server");
        menu.put(4, "Change the root directory of the server");
        menu.put(5, "Change the maintenance directory of the server");
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
        if (!serverISRunning) {
            for (int i = 3; i < 6; i++) {
                System.out.println(i + " - " + getMenu().get(i));
            }
        } else {
            if (currentState == 1) {
                System.out.println(5 + " - " + getMenu().get(5));
            }
            if (currentState == 2) {
                System.out.println(4 + " - " + getMenu().get(4));
            }
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

    
