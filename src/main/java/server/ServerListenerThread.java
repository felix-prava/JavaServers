package server;

import client.HttpConnectionWorkerThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public final class ServerListenerThread extends Thread{

    protected int port;
    private String rootDirectory, maintenanceDirectory;
    private ServerSocket serverSocket;
    private boolean serverStatus;
    private ServerManager serverManager;

    public ServerListenerThread(int _port, String _rootDirectory, String _maintenanceDirectory, ServerManager _serverManager) throws IOException {
        this.port = _port;
        this.rootDirectory = _rootDirectory;
        this.maintenanceDirectory = _maintenanceDirectory;
        this.serverSocket = new ServerSocket(this.port);
        this.serverManager = _serverManager;
    }

    public void setPort(int _port) {
        this.port = _port;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String _rootDirectory) { this.rootDirectory = _rootDirectory; }

    public String getMaintenanceDirectory() { return maintenanceDirectory; }

    public void setMaintenanceDirectory(String _maintenanceDirectory) {
        this.maintenanceDirectory = _maintenanceDirectory;
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    public boolean getServerStatus() { return serverStatus; }

    public void setServerStatus(boolean _status) { this.serverStatus = _status; }

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
