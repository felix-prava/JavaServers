package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerListenerThreadTest {

    private ServerManager serverManager;

    @BeforeEach
    void setup() {
        serverManager = new ServerManager();
    }

    @Test
    void setPortTest() throws IOException {
        serverManager.setPort(3000);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3000, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverListenerThread.setPort(8080);
        assertEquals(8080, serverListenerThread.port);
        assertNotEquals(3000, serverListenerThread.port);
    }

    @Test
    void getRootDirectoryTest() throws IOException {
        serverManager.setPort(3001);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3001, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        assertEquals("clientWebsite\\rootDirectory\\",
                serverListenerThread.getRootDirectory());
        serverListenerThread.setRootDirectory("\\");
        assertNotEquals("Home", serverListenerThread.getRootDirectory());
    }

    @Test
    void setRootDirectoryTest() throws IOException {
        serverManager.setPort(3002);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3002, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverListenerThread.setRootDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverListenerThread.getRootDirectory());
        assertNotEquals("\\home", serverListenerThread.getRootDirectory());
    }

    @Test
    void getMaintenanceDirectoryTest() throws IOException {
        serverManager.setPort(3003);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3003, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        assertEquals("clientWebsite\\maintenanceDirectory\\",
                serverListenerThread.getMaintenanceDirectory());
        serverListenerThread.setMaintenanceDirectory("\\");
        assertNotEquals("Home", serverListenerThread.getMaintenanceDirectory());
    }

    @Test
    void setMaintenanceDirectoryTest() throws IOException {
        serverManager.setPort(3004);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3004, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverListenerThread.setMaintenanceDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverListenerThread.getMaintenanceDirectory());
        assertNotEquals("\\home", serverListenerThread.getMaintenanceDirectory());
    }

    @Test
    void getServerSocketTest() throws IOException {
        serverManager.setPort(3005);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3005, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        ServerSocket serverSocket = new ServerSocket(7005);
        assertTrue(serverSocket.getLocalPort() - 4000 == serverListenerThread.getServerSocket().getLocalPort());
    }

    @Test
    void setServerStatusTest() throws IOException {
        serverManager.setPort(3006);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3006, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverManager.serverListenerThread = serverListenerThread;

        // it should be false initially
        assertFalse(serverListenerThread.getServerStatus());

        //the server is running on normal mode
        serverManager.setServerOnNormalRunningMode();
        assertTrue(serverListenerThread.getServerStatus());
    }

    @Test
    void getServerStatusTest() throws IOException {
        serverManager.setPort(3007);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3007, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        // it should be false initially
        assertFalse(serverListenerThread.getServerStatus());
        serverListenerThread.setServerStatus(true);
        assertTrue(serverListenerThread.getServerStatus());
    }

    @Test
    void getResourcesMapTest() throws IOException {
        serverManager.setPort(3008);
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(3008, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);

        HashMap<String, String> resourcesMap = new HashMap<>();
        // it should be null initially
        assertNull(serverListenerThread.getResourcesMap());

        serverManager.setHTMLFiles(resourcesMap);
        assertTrue(serverListenerThread.getResourcesMap().isEmpty());
        resourcesMap.put("key", "value");

        assertEquals(1, serverListenerThread.getResourcesMap().size());
        assertTrue(serverListenerThread.getResourcesMap().containsKey("key"));
        assertFalse(serverListenerThread.getResourcesMap().containsValue("valuee"));
    }

    /*
    @Test
    void runTest() {
    }
    */
}