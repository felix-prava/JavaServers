import admin.AdminManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ServerManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class NormalModeServerControllerTest {

    private NormalModeServerController normalModeServerController;
    private AdminManager adminManager;

    @BeforeEach
    void setup() {
        NormalModeServerController controller = new NormalModeServerController();
        // spying on real object
        normalModeServerController = spy(controller);
        // mock Creation
        adminManager = mock(AdminManager.class);
        normalModeServerController.setAdminManager(adminManager);
    }

    @Test
    void setAdminManagerTest() {
        AdminManager secondAdminManager = new AdminManager();
        assertNotEquals(secondAdminManager, normalModeServerController.adminManager);
        normalModeServerController.setAdminManager(secondAdminManager);
        assertEquals(secondAdminManager, normalModeServerController.adminManager);
    }

    @Test
    void closeServerTest() {
        normalModeServerController.closeServer();
        // verify that closeServer() method in spying NormalModeServerController object have been invoked
        verify(normalModeServerController).closeServer();
        // verify that stopServer() method in mocked AdminManager object have been invoked
        verify(adminManager).stopServer();
    }

    @Test
    void putServerOnMaintenanceModeTest() {
        normalModeServerController.putServerOnMaintenanceMode();
        // verify that putServerOnMaintenanceMode() method in spying NormalModeServerController object have been invoked
        verify(normalModeServerController).putServerOnMaintenanceMode();
        // verify that setServerOnMaintenanceMode() method in mocked AdminManager object have been invoked
        verify(adminManager).setServerOnMaintenanceMode();
    }

    @Test
    void changeMaintenanceDirectoryTest() {
        normalModeServerController.changeMaintenanceDirectory();
        // verify that changeMaintenanceDirectory() method in spying NormalModeServerController object have been invoked
        verify(normalModeServerController).changeMaintenanceDirectory();
        // verify that changeMaintenanceDirectory(boolean) method in mocked AdminManager object have been invoked
        verify(adminManager).changeMaintenanceDirectory(false);
    }
}