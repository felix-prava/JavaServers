package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public final class ServerManager {
    public static final int DEFAULT_PORT = 3000;

    protected int port = DEFAULT_PORT;
    protected String rootDirectory = "clientWebsite\\rootDirectory\\";
    protected String maintenanceDirectory = "clientWebsite\\maintenanceDirectory\\";
    protected static ServerListenerThread serverListenerThread = null;
    protected boolean serverISRunning = false;
    private HashMap<String, String> resourcesMap;

    public void setPort(int portReceived) {
        this.port = portReceived;
        if (serverListenerThread != null) {
            serverListenerThread.setPort(port);
        }
    }

    public void setRootDirectory(String rootDirectoryReceived) {
        System.out.println(rootDirectoryReceived);
        this.rootDirectory = rootDirectoryReceived;
        if (serverListenerThread != null) {
            serverListenerThread.setRootDirectory(rootDirectory);
        }
    }

    public void setMaintenanceDirectory(String maintenanceDirectoryReceived) {
        this.maintenanceDirectory = maintenanceDirectoryReceived;
        if (serverListenerThread != null) {
            serverListenerThread.setMaintenanceDirectory(maintenanceDirectory);
        }
    }

    public void setServerOnMaintenanceMode() {
        if (!serverISRunning) {
            serverISRunning = true;
            this.openServer();
        }
        serverListenerThread.setServerStatus(false);
    }

    public void setServerOnNormalRunningMode() {
        if (!serverISRunning) {
            serverISRunning = true;
            this.openServer();
        }
        serverListenerThread.setServerStatus(true);
    }

    public void closeServer() {
        if (serverListenerThread != null) {
            ServerSocket serverSocket = serverListenerThread.getServerSocket();
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        serverListenerThread = null;
        serverISRunning = false;
    }

    public void openServer() {
        if (serverListenerThread == null) {
            try {
                serverListenerThread = new ServerListenerThread(port, rootDirectory, maintenanceDirectory, this);
                serverListenerThread.start();
            } catch (IOException e) {
                System.out.println("Input Output Exception");
            }
        }
    }

    public void setHTMLFiles(HashMap<String, String> resourcesMapReceived) {
        this.resourcesMap = resourcesMapReceived;
    }

    public HashMap<String, String> getResourcesMap() {
        return resourcesMap;
    }
}
