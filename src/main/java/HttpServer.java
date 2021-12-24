import admin.AdminManager;

public final class HttpServer {

    public static void main(String[] args) {
        AdminManager adminManager = new AdminManager();

        ServerStoppedController serverStoppedController = new ServerStoppedController();
        NormalModeServerController normalModeServerController = new NormalModeServerController();
        MaintenanceModeController maintenanceModeController = new MaintenanceModeController();

        serverStoppedController.setAdminManager(adminManager);
        normalModeServerController.setAdminManager(adminManager);
        maintenanceModeController.setAdminManager(adminManager);

        adminManager.startProgram();
    }

    // C:\Users\Mihai\Desktop\test
    // C:\Users\Mihai\Desktop\maintenanceDirectory
}
