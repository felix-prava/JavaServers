import admin.AdminManager;

public class ServerStoppedController {
    private static AdminManager adminManager;

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public void startServerNormalMode() {
        adminManager.startServer();
    }

    public void updatePort() {
        adminManager.updatePort();
    }

    public void changeRootDirectory() {
        adminManager.changeRootDirectory(true);
    }

    public void changeMaintenanceDirectory() {
        adminManager.changeMaintenanceDirectory(true);
    }
}
