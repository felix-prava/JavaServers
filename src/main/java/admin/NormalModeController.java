package admin;

import java.io.IOException;

public class NormalModeController {

    private static AdminManager adminManager;

    public static void setAdminManager(AdminManager adminManagerReceived) {
        adminManager = adminManagerReceived;
    }

    public static void startServerNormalMode() throws IOException {
        System.out.println("BBBB");
        if (adminManager != null) {
            adminManager.startServer();
            System.out.println("CCCC");
        }
    }

    public static void stopServer() throws IOException {
        if (adminManager != null) {
            adminManager.stopServer();
        }
    }

    public static void serverStoppedUpdatePort() throws IOException {
        adminManager.serverStoppedUpdatePort();
    }
}
