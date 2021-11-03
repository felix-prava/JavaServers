package Server;

import Client.HttpConnectionWorkerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{

    private int port;
    private String rootDirectory, maintenanceDirectory;
    private ServerSocket serverSocket;
    private boolean serverStatus;

    public ServerListenerThread(int port, String rootDirectory, String maintenanceDirectory) throws IOException {
        this.port = port;
        this.rootDirectory = rootDirectory;
        this.maintenanceDirectory = maintenanceDirectory;
        this.serverSocket = new ServerSocket(this.port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public String getMaintenanceDirectory() { return maintenanceDirectory; }

    public void setMaintenanceDirectory(String maintenanceDirectory) {
        this.maintenanceDirectory = maintenanceDirectory;
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    public boolean getServerStatus() { return serverStatus; }

    public void setServerStatus(boolean status) { this.serverStatus = status; }

    @Override
    public void run() {
        //super.run();
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("-----Connection accepted------ " + socket.getInetAddress());

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

    public boolean isServerStatus() {
        return serverStatus;
    }
}
