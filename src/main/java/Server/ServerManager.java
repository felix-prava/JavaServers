package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerManager {
    private int port = 3000;
    private String rootDirectory = "C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\rootDirectory\\";
    private String maintenanceDirectory = "C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\maintenanceDirectory\\";
    private static ServerListenerThread serverListenerThread = null;
    private boolean serverISRunning = false;

    public void setPort(int port) {
        this.port = port;
        serverListenerThread.setPort(port);
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        serverListenerThread.setRootDirectory(rootDirectory);
    }

    public void setMaintenanceDirectory(String maintenanceDirectory) {
        this.maintenanceDirectory = maintenanceDirectory;
        serverListenerThread.setMaintenanceDirectory(maintenanceDirectory);
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
                serverListenerThread = new ServerListenerThread(port, rootDirectory, maintenanceDirectory);
                serverListenerThread.start();
            } catch (IOException e) {
                System.out.println("Input Output Exception");
            }
        }
    }
}
