package Admin;

import Server.ServerManager;

import java.util.Scanner;

public class AdminManager {
    private ServerState serverState = new ServerState(0); //server is stopped
    private ServerManager serverManager = new ServerManager();

    public void initializeOptions() {
        serverState.initializeOptions();
    }

    public int getServerState(ServerState serverState) {
        return serverState.processServerState();
    }

    public void handleOptions() {
        int state = getServerState(this.serverState);

        while (state != -1) {
            switch (state) {
                case 0: {
                    serverManager.closeServer();
                    break;
                }
                case 1: {
                    serverManager.setHTMLFiles(serverState.getResourcesMap());
                    serverManager.setServerOnNormalRunningMode();
                    break;
                }
                case 2: {
                    serverManager.setServerOnMaintenanceMode();
                    break;
                }
                case 3: {
                    serverManager.setPort(serverState.getPort());
                    break;
                }
                case 4: {
                    serverManager.setRootDirectory(serverState.getRootDirectory());
                    serverManager.setHTMLFiles(serverState.getResourcesMap());
                    break;
                }
                case 5: {
                    serverManager.setMaintenanceDirectory(serverState.getMaintenanceDirectory());
                    break;
                }
                default: break;
            }
            state = serverState.processServerState();
        }
        serverManager.closeServer();
        System.out.println("Program is closed successfully");
    }
}
