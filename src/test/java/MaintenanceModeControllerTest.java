import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import admin.AdminManager;

import org.junit.jupiter.api.BeforeEach;

class MaintenanceModeControllerTest {

    private MaintenanceModeController maintenanceModeController;
    private AdminManager adminManager;

    @BeforeEach
    void setup() {
        MaintenanceModeController controller = new MaintenanceModeController();
        // spying on real object
        maintenanceModeController = spy(controller);
        // mock Creation
        adminManager = mock(AdminManager.class);
        maintenanceModeController.setAdminManager(adminManager);

    }

    @Test
    void setAdminManagerTest() {
        AdminManager secondAdminManager = new AdminManager();
        assertNotEquals(secondAdminManager, maintenanceModeController.adminManager);
        maintenanceModeController.setAdminManager(secondAdminManager);
        assertEquals(secondAdminManager, maintenanceModeController.adminManager);
    }

    @Test
    void closeServerTest() {
        maintenanceModeController.closeServer();
        // verify that closeServer() method in spying MaintenanceModeController object have been invoked
        verify(maintenanceModeController).closeServer();
        // verify that stopServer() method in mocked AdminManager object have been invoked
        verify(adminManager).stopServer();
    }

    @Test
    void putServerOnNormalModeTest() {
        maintenanceModeController.putServerOnNormalMode();
        // verify that putServerOnNormalMode() method in spying MaintenanceModeController object have been invoked
        verify(maintenanceModeController).putServerOnNormalMode();
        // verify that setServerOnNormalMode() method in mocked AdminManager object have been invoked
        verify(adminManager).setServerOnNormalMode();
    }

    @Test
    void changeRootDirectoryTest() {
        maintenanceModeController.changeRootDirectory();
        // verify that changeRootDirectory() method in spying MaintenanceModeController object have been invoked
        verify(maintenanceModeController).changeRootDirectory();
        // verify that changeRootDirectory(false) method in mocked AdminManager object have been invoked
        verify(adminManager).changeRootDirectory(false);
    }
}