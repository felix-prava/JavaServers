package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public final class ServerManager {
    protected int port = 3000;
    protected String rootDirectory = "clientWebsite\\rootDirectory\\";
    protected String maintenanceDirectory = "clientWebsite\\maintenanceDirectory\\";
    protected static ServerListenerThread serverListenerThread = null;
    protected boolean serverISRunning = false;
    private HashMap<String, String> resourcesMap;

    public void setPort(int _port) {
        this.port = _port;
        if (serverListenerThread != null) {
            serverListenerThread.setPort(port);
        }
    }

    public void setRootDirectory(String _rootDirectory) {
        this.rootDirectory = _rootDirectory;
        if (serverListenerThread != null) {
            serverListenerThread.setRootDirectory(rootDirectory);
        }
    }

    public void setMaintenanceDirectory(String _maintenanceDirectory) {
        this.maintenanceDirectory = _maintenanceDirectory;
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

    public void setHTMLFiles(HashMap<String, String> _resourcesMap) {
        this.resourcesMap = _resourcesMap;
    }

    public HashMap<String, String> getResourcesMap() {
        return resourcesMap;
    }
}
