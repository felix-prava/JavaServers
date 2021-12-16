import admin.AdminManager;

public final class NormalModeServerController {
    private static AdminManager adminManager;

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public void closeServer() {
        adminManager.stopServer();
    }

    public void putServerOnMaintenanceMode() {
        adminManager.setServerOnMaintenanceMode();
    }

    public void changeMaintenanceDirectory() {
        adminManager.changeMaintenanceDirectory(false);
    }

}
