import admin.AdminManager;

import java.io.IOException;

public final class MainController {
    private static AdminManager adminManager;

    public void closeServer() {
        adminManager.stopServer();
    }

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public void startServerNormalMode() {
        adminManager.startServer();
    }

    public void updatePort() {
        adminManager.updatePort();
    }

    public void putServerOnMaintenanceMode() {
        adminManager.setServerOnMaintenanceMode();
    }

    public void putServerOnNormalMode() {
        adminManager.setServerOnNormalMode();
    }

    public void changeRootDirectory1() {
        adminManager.changeRootDirectory(true);
    }

    public void changeRootDirectory2() {
        adminManager.changeRootDirectory(false);
    }

    public void changeMaintenanceDirectory1() {
        adminManager.changeMaintenanceDirectory(true);
    }

    public void changeMaintenanceDirectory2() {
        adminManager.changeMaintenanceDirectory(false);
    }
}
