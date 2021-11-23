package admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class ServerState {
    public static final int DEFAULT_PORT = 3000;
    public static final int DEFAULT_ERROR_CODE = -10;
    public static final int DEFAULT_VALUE_FOR_MAP_EXPLANATION = 100;

    private int state;
    private int port = DEFAULT_PORT;
    private Map<Integer, String> menu;
    private String rootDirectory = "clientWebsite\\rootDirectory\\";
    private String maintenanceDirectory = "clientWebsite\\maintenanceDirectory\\";
    private boolean serverISRunning = false;
    private HashMap<String, String> resourcesMap = new HashMap<>();

    public ServerState(int _state) { this.state = _state; }

    public int getState() { return state; }

    public void setState(int _state) { this.state = _state; }

    public void setPort(int _port) { this.port = _port; }

    public void setRootDirectory(String _rootDirectory) { this.rootDirectory = _rootDirectory; }

    public void setMaintenanceDirectory(String _maintenanceDirectory) { this.maintenanceDirectory = _maintenanceDirectory; }

    public Map<Integer, String> getMenu() { return menu; }

    public int processServerState() {
        Scanner scan = new Scanner(System.in);
        int currentState = getState();
        System.out.println("The current state of the server is: " + currentState + getMenu().get(state + DEFAULT_VALUE_FOR_MAP_EXPLANATION));
        showMenu(currentState);
        System.out.print("Your option: ");
        String input = scan.nextLine();
        System.out.println();
        if (!isNumber(input)) {
            System.out.print("This is not a valid option!\n");
            return DEFAULT_ERROR_CODE;
        }
        int receivedState = Integer.parseInt(input);
        if (!(receivedState >= -1 && receivedState <= 5)) {
            System.out.println("This is not a valid state\n");
            return DEFAULT_ERROR_CODE;
        } else if (receivedState == currentState) {
            System.out.println("The server is already in this state\n");
            return DEFAULT_ERROR_CODE;
        } else if (receivedState > 2) {
            if (!serverISRunning) {
                if (receivedState == 3) {
                    System.out.print("Type the value of the new port: ");
                    String portInput = scan.nextLine();
                    System.out.println();
                    if (!isNumber(portInput)) {
                        System.out.print("This is not a valid value for the port!\n");
                        return DEFAULT_ERROR_CODE;
                    }
                    setPort(Integer.parseInt(portInput));
                    System.out.println("The new port is: " + this.port + "\n");
                    return 3;
                } else if (receivedState == 4) {
                    System.out.print("Type the path of the new root directory: ");
                    String newRootDirectory = scan.nextLine();
                    System.out.println();
                    if (!pathIsCorrect(newRootDirectory, true)) {
                        return DEFAULT_ERROR_CODE;
                    }
                    if (!newRootDirectory.endsWith("\\")) {
                        newRootDirectory += "\\";
                    }
                    setRootDirectory(newRootDirectory);
                    updateResources();
                    System.out.println("The new root directory path is: " + this.rootDirectory + "\n");
                    return 4;
                } else {
                    System.out.print("Type the path of the new maintenance directory: ");
                    String newMaintenanceDirectory = scan.nextLine();
                    System.out.println();
                    if (!pathIsCorrect(newMaintenanceDirectory, false)) {
                        return DEFAULT_ERROR_CODE;
                    }
                    if (!newMaintenanceDirectory.endsWith("\\")) {
                        newMaintenanceDirectory += "\\";
                    }
                    setMaintenanceDirectory(newMaintenanceDirectory);
                    System.out.println("The new maintenance directory path is: " + this.maintenanceDirectory + "\n");
                    return 5;
                }
            } else {
                if (currentState == 1) {
                    if (receivedState == 5) {
                        System.out.print("Type the path of the new maintenance directory: ");
                        String newMaintenanceDirectory = scan.nextLine();
                        System.out.println();
                        if (!pathIsCorrect(newMaintenanceDirectory, false)) {
                            return DEFAULT_ERROR_CODE;
                        }
                        if (!newMaintenanceDirectory.endsWith("\\")) {
                            newMaintenanceDirectory += "\\";
                        }
                        setMaintenanceDirectory(newMaintenanceDirectory);
                        System.out.println("The new maintenance directory path is: " + this.maintenanceDirectory + "\n");
                        return 5;
                    }
                    System.out.println("This is not a valid state\n");
                }
                if (currentState == 2) {
                    if (receivedState == 4) {
                        System.out.print("Type the path of the new root directory: ");
                        String newRootDirectory = scan.nextLine();
                        System.out.println();
                        if (!pathIsCorrect(newRootDirectory, true)) {
                            return DEFAULT_ERROR_CODE;
                        }
                        if (!newRootDirectory.endsWith("\\")) {
                            newRootDirectory += "\\";
                        }
                        setRootDirectory(newRootDirectory);
                        updateResources();
                        System.out.println("The new root directory path is: " + this.rootDirectory + "\n");
                        return 4;
                    }
                    System.out.println("This is not a valid state\n");
                }
                return DEFAULT_ERROR_CODE;
            }
        } else {
            setState(receivedState);
            serverISRunning = (receivedState > 0);
            System.out.println("The server state was changed\n");
            return receivedState;
        }
    }

    protected void updateResources() {
        try {
            resourcesMap.clear();
            File[] files = new File(rootDirectory).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".html")) {
                        if (file.getName().equals("index.html")) {
                            resourcesMap.put("/", "index.html");
                            resourcesMap.put("/index.html", "index.html");
                            resourcesMap.put("/localhost:3000", "index.html");
                        } else {
                            resourcesMap.put("/" + file.getName(), file.getName());
                        }
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    protected boolean pathIsCorrect(String directoryPath, boolean isRootDirectory) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("This path does not correspond to a directory\n");
            return false;
        }
        if (isRootDirectory) {
            if (!new File(directoryPath, "pageNotFound.html").exists()) {
                System.out.println("This directory does not have a pageNotFound.html file\n");
                return false;
            }
        } else {
            if (!new File(directoryPath, "maintenance.html").exists()) {
                System.out.println("This directory does not have a maintenance.html file\n");
                return false;
            }
        }
        return true;
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
        menu.put(DEFAULT_VALUE_FOR_MAP_EXPLANATION, " and this means that the server is stopped");
        menu.put(DEFAULT_VALUE_FOR_MAP_EXPLANATION + 1, " and this means that the server is in normal mode");
        menu.put(DEFAULT_VALUE_FOR_MAP_EXPLANATION + 2, " and this means that the server is in maintenance mode");
        updateResources();
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

    protected boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
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

    public HashMap<String, String> getResourcesMap() {
        return resourcesMap;
    }
}

    
