import admin.AdminManager;

public final class MaintenanceModeController {
    private static AdminManager adminManager;

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public void closeServer() {
        adminManager.stopServer();
    }

    public void putServerOnNormalMode() {
        adminManager.setServerOnNormalMode();
    }

    public void changeRootDirectory() {
        adminManager.changeRootDirectory(false);
    }
}
