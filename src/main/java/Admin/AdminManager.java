package Admin;

import Server.ServerManager;

public class AdminManager {

    public void handleOptions() {
        int state = 0; //server is stopped
        ServerState serverState = new ServerState(state);
        ServerManager serverManager = new ServerManager();
        serverState.initializeOptions();
        state = serverState.processServerState();

        while (state != -1) {
            switch (state) {
                case 0: {
                    serverManager.closeServer();
                    break;
                }
                case 1: {
                    serverManager.setResourceMap(serverState.getResourceMap());
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
                    serverManager.setResourceMap(serverState.getResourceMap());
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
