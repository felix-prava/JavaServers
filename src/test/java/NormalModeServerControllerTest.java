import admin.AdminManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ServerManager;

import static org.junit.jupiter.api.Assertions.*;

class NormalModeServerControllerTest {

    private NormalModeServerController normalModeServerController;
    private AdminManager adminManager;

    @BeforeEach
    void setup() {
        normalModeServerController = new NormalModeServerController();
        AdminManager adminManager = new AdminManager();
        this.adminManager = adminManager;
        normalModeServerController.setAdminManager(adminManager);
    }

    @Test
    void setAdminManagerTest() {
        AdminManager secondAdminManager = new AdminManager();
        /* assertNotEquals(normalModeServerController.adminManager, secondAdminManager);
        normalModeServerController.setAdminManager(secondAdminManager);
        assertEquals(normalModeServerController.adminManager, secondAdminManager); */
    }

    @Test
    void closeServerTest() {
        //adminManager.startProgram();
        //adminManager.startServer();
        //assertFalse(adminManager.getServerManager().getResourcesMap().isEmpty());
    }

    @Test
    void putServerOnMaintenanceModeTest() {
    }

    @Test
    void changeMaintenanceDirectoryTest() {
    }
}