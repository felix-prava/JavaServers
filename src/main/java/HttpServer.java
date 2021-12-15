import admin.AdminManager;
import admin.NormalModeController;

public final class HttpServer {
    private AdminManager adminManager;

    public static void main(String[] args) {
        AdminManager adminManager = new AdminManager();
        MainController mainController = new MainController();
        mainController.setAdminManager(adminManager);
        NormalModeController.setAdminManager(adminManager);
        adminManager.initializeOptions();
        adminManager.handleOptions();
    }

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
}
