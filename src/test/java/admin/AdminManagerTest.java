package admin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import server.ServerManager;

import static org.mockito.Mockito.*;

public class AdminManagerTest {

    static AdminManager adminManager;
    static ServerManager serverManager;
    static ServerState serverState;

    @BeforeAll
    public static void setup() {
        AdminManager admin = new AdminManager();
        adminManager = spy(admin);
        // adminManager.startProgram();
        serverManager = mock(ServerManager.class);
        serverState = mock(ServerState.class);

        adminManager.setServerManager(serverManager);
        adminManager.setServerState(serverState);
    }

    @Test
    public void startServerTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).restoreErrorMessages();
            adminManager.startServer();
            verify(adminManager).startServer();
            verify(serverManager).closeServer();
            verify(serverManager).setHTMLFiles(any());
            verify(serverManager).setServerOnNormalRunningMode();
        } catch (RuntimeException e) {

        }
    }

    @Test
    void setServerOnMaintenanceModeTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).restoreErrorMessages();
            adminManager.setServerOnMaintenanceMode();
            verify(adminManager).setServerOnMaintenanceMode();
            verify(serverManager).setServerOnMaintenanceMode();
        } catch (RuntimeException e) {

        }
    }

    @Test
    void setServerOnNormalModeTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).restoreErrorMessages();
            adminManager.setServerOnNormalMode();
            verify(adminManager).setServerOnNormalMode();
            verify(serverManager).setServerOnNormalRunningMode();
        } catch (RuntimeException e) {

        }
    }

    @Test
    void stopServerTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).restoreErrorMessages();
            adminManager.stopServer();
            verify(adminManager).stopServer();
            verify(serverManager).closeServer();
        } catch (RuntimeException e) {

        }
    }

    @Test
    void updatePortTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).updatePort();
            adminManager.updatePort();
            fail("updatePort method have not been invoked.");
        } catch (RuntimeException e) {

        }
    }

    @Test
    void changeRootDirectoryTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).changeRootDirectory(anyBoolean());
            adminManager.changeRootDirectory(true);
            fail("changeRootDirectory method have not been invoked.");
        } catch (RuntimeException e) {

        }
    }

    @Test
    void changeMaintenanceDirectoryTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).changeMaintenanceDirectory(anyBoolean());
            adminManager.changeMaintenanceDirectory(true);
            fail("changeMaintenanceDirectory method have not been invoked.");
        } catch (RuntimeException e) {

        }
    }

    @Test
    void restoreErrorMessagesTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).restoreErrorMessages();
            adminManager.restoreErrorMessages();
            fail("restoreErrorMessages method have not been invoked.");
        } catch (RuntimeException e) {

        }
    }

    @Test
    void displayErrorMessageTest() {
        try {
            doThrow(new RuntimeException()).when(adminManager).displayErrorMessage(anyString());
            adminManager.displayErrorMessage("test message");;
            fail("displayErrorMessage method have not been invoked.");
        } catch (RuntimeException e) {

        }
    }

    @Test
    void portIsValidTest() {
        assertTrue(adminManager.portIsValid("1000"));
        assertTrue(adminManager.portIsValid("5000"));
        assertTrue(adminManager.portIsValid("10000"));

        assertFalse(adminManager.portIsValid("999"));
        assertFalse(adminManager.portIsValid("10001"));
    }

}
