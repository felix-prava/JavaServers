import admin.AdminManager;
import admin.NormalModeController;

public final class HttpServer {

    public static void main(String[] args) {
        AdminManager adminManager = new AdminManager();
        MainController mainController = new MainController();
        mainController.setAdminManager(adminManager);
        adminManager.initializeOptions();
        adminManager.handleOptions();
    }
}
