import Admin.AdminManager;

public class HttpServer {

    public static void main(String[] args) {
        AdminManager adminManager = new AdminManager();
        adminManager.handleOptions();
    }
}
