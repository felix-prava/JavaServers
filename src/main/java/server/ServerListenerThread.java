package server;

import client.HttpConnectionWorkerThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerListenerThread extends Thread{

    protected int port;
    private String rootDirectory, maintenanceDirectory;
    private ServerSocket serverSocket;
    private boolean serverStatus;
    private ServerManager serverManager;

    public ServerListenerThread(int portReceived, String rootDirectoryReceived, String maintenanceDirectoryReceived, ServerManager serverManagerReceived) throws IOException {
        this.port = portReceived;
        this.rootDirectory = rootDirectoryReceived;
        this.maintenanceDirectory = maintenanceDirectoryReceived;
        this.serverSocket = new ServerSocket(this.port);
        this.serverManager = serverManagerReceived;
    }

    public void setPort(int portReceived) {
        this.port = portReceived;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectoryReceived) { this.rootDirectory = rootDirectoryReceived; }

    public String getMaintenanceDirectory() { return maintenanceDirectory; }

    public void setMaintenanceDirectory(String maintenanceDirectoryReceived) {
        this.maintenanceDirectory = maintenanceDirectoryReceived;
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    public boolean getServerStatus() { return serverStatus; }

    public void setServerStatus(boolean statusReceived) { this.serverStatus = statusReceived; }

    public HashMap<String, String> getResourcesMap() {
        return serverManager.getResourcesMap();
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket, this);
                workerThread.start();
            }
        } catch (IOException e) {
            if (!serverSocket.isClosed()) {
                System.err.println("Problem with setting socket:\n");
                System.err.println(e);
            }
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
