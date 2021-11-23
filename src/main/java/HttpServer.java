import admin.AdminManager;

public final class HttpServer {

    public static void main(String[] args) {
        AdminManager adminManager = new AdminManager();
        adminManager.initializeOptions();
        adminManager.handleOptions();
    }
}
