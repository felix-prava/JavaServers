package Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerListenerThreadTest {

    private ServerListenerThread serverListenerThread;
    private ServerManager serverManager;

    @BeforeEach
    void setup() throws IOException {
        serverManager = new ServerManager();
        serverListenerThread =
                new ServerListenerThread(3000, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
    }

    @Test
    void setPortTest() {
        serverListenerThread.setPort(8080);
        assertEquals(8080, serverListenerThread.port);
        assertNotEquals(3000, serverListenerThread.port);
    }

    @Test
    void getRootDirectoryTest() {
    }

    @Test
    void setRootDirectoryTest() {
        serverListenerThread.setRootDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverListenerThread.getRootDirectory());
        assertNotEquals("\\home", serverListenerThread.getRootDirectory());
    }

    @Test
    void getMaintenanceDirectoryTest() {
    }

    @Test
    void setMaintenanceDirectoryTest() {
        serverListenerThread.setMaintenanceDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverListenerThread.getMaintenanceDirectory());
        assertNotEquals("\\home", serverListenerThread.getMaintenanceDirectory());
    }

    @Test
    void getServerSocketTest() {
    }

    @Test
    void getServerStatusTest() {
    }

    @Test
    void setServerStatusTest() {
    }

    @Test
    void getResourcesMapTest() {
    }

    /*
    @Test
    void runTest() {
    }
    */
}