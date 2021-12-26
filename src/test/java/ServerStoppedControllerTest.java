import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import admin.AdminManager;

import org.junit.jupiter.api.BeforeEach;

public class ServerStoppedControllerTest {

    private ServerStoppedController serverStoppedController;
    private AdminManager adminManager;

    @BeforeEach
    void setup() {
        ServerStoppedController controller = new ServerStoppedController();
        // spying on real object
        serverStoppedController = spy(controller);
        // mock Creation
        adminManager = mock(AdminManager.class);
        serverStoppedController.setAdminManager(adminManager);
    }

    @Test
    void setAdminManagerTest() {
        AdminManager secondAdminManager = new AdminManager();
        assertNotEquals(secondAdminManager, serverStoppedController.adminManager);
        serverStoppedController.setAdminManager(secondAdminManager);
        assertEquals(secondAdminManager, serverStoppedController.adminManager);
    }

    @Test
    void startServerNormalModeTest() {
        serverStoppedController.startServerNormalMode();
        // verify that startServerNormalMode() method in spying ServerStoppedController object have been invoked
        verify(serverStoppedController).startServerNormalMode();
        // verify that startServer() method in mocked AdminManager object have been invoked
        verify(adminManager).startServer();
    }

    @Test
    void updatePortTest() {
        serverStoppedController.updatePort();
        // verify that updatePort() method in spying ServerStoppedController object have been invoked
        verify(serverStoppedController).updatePort();
        // verify that updatePort() method in mocked AdminManager object have been invoked
        verify(adminManager).updatePort();
    }

    @Test
    void changeRootDirectoryTest() {
        serverStoppedController.changeRootDirectory();
        // verify that changeRootDirectory() method in spying ServerStoppedController object have been invoked
        verify(serverStoppedController).changeRootDirectory();
        // verify that changeRootDirectory(boolean) method in mocked AdminManager object have been invoked
        verify(adminManager).changeRootDirectory(true);
    }

    @Test
    void changeMaintenanceDirectoryTest() {
        serverStoppedController.changeMaintenanceDirectory();
        // verify that changeMaintenanceDirectory() method in spying ServerStoppedController object have been invoked
        verify(serverStoppedController).changeMaintenanceDirectory();
        // verify that changeMaintenanceDirectory(boolean) method in mocked AdminManager object have been invoked
        verify(adminManager).changeMaintenanceDirectory(true);
    }
}