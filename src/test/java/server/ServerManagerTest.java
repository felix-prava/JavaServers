package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerManagerTest {

    private ServerManager serverManager;

    @BeforeEach
    void setup() {
        serverManager = new ServerManager();
    }

    @Test
    void openServerTest() {
        ServerManager secondServerManager = new ServerManager();
        assertNull(secondServerManager.serverListenerThread);

        secondServerManager.port = 2345;
        secondServerManager.openServer();
        assertNotNull(secondServerManager.serverListenerThread);
        assertEquals(secondServerManager.serverISRunning, true);

        secondServerManager.closeServer();
        secondServerManager.port = 2345;
        secondServerManager.openServer();
        assertEquals(secondServerManager.serverISRunning, true);
        assertEquals(secondServerManager.serverListenerThread.port, 2345);
    }

    @Test
    void setHTMLFilesTest() {
        // empty HashMap
        HashMap<String, String> resourcesMap = new HashMap<>();
        serverManager.setHTMLFiles(resourcesMap);
        assertTrue(serverManager.getResourcesMap().isEmpty());

        HashMap<String, String> newResourcesMap = new HashMap<>();
        newResourcesMap.put("key", "value");
        serverManager.setHTMLFiles(newResourcesMap);

        assertEquals(1, serverManager.getResourcesMap().size());
        assertTrue(serverManager.getResourcesMap().containsKey("key"));
        assertFalse(serverManager.getResourcesMap().containsValue("value24"));

        serverManager.closeServer();
    }


    @Test
    void closeServerTest() {
        ServerManager secondServerManager = new ServerManager();
        assertNull(secondServerManager.serverListenerThread);

        secondServerManager.port = 3456;
        secondServerManager.openServer();
        assertNotNull(secondServerManager.serverListenerThread);

        secondServerManager.closeServer();
        assertNull(secondServerManager.serverListenerThread);
        assertEquals(secondServerManager.serverISRunning, false);
    }

    @Test
    void getResourcesMapTest() {
        HashMap<String, String> resourcesMap = new HashMap<>();
        // it should be null initially
        assertNull(serverManager.getResourcesMap());

        serverManager.setHTMLFiles(resourcesMap);
        assertTrue(serverManager.getResourcesMap().isEmpty());
        resourcesMap.put("key", "value");

        assertEquals(1, serverManager.getResourcesMap().size());
        assertTrue(serverManager.getResourcesMap().containsKey("key"));
        assertFalse(serverManager.getResourcesMap().containsValue("valuee"));
    }

    @Test
    void setPortTest() {
        serverManager.setPort(8080);
        assertEquals(8080, serverManager.port);
        assertNotEquals(3000, serverManager.port);
    }

    @Test
    void setRootDirectoryTest() throws IOException {
        serverManager.setRootDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverManager.rootDirectory);
        assertNotEquals("\\root", serverManager.rootDirectory);

        serverManager.setRootDirectory("\\root");
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(serverManager.port, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        assertEquals("\\root", serverListenerThread.getRootDirectory());
    }

    @Test
    void setMaintenanceDirectoryTest() throws IOException {
        serverManager.setMaintenanceDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverManager.maintenanceDirectory);
        assertNotEquals("\\home", serverManager.maintenanceDirectory);

        serverManager.setMaintenanceDirectory("\\home");
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(4200, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        assertEquals("\\home", serverListenerThread.getMaintenanceDirectory());
    }

    @Test
    void setServerOnMaintenanceModeTest() throws IOException {
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(10000, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverManager.serverListenerThread = serverListenerThread;

        serverManager.setServerOnMaintenanceMode();
        assertFalse(serverManager.serverListenerThread.getServerStatus());
    }

    @Test
    void setServerNormalModeTest() throws  IOException {
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(10004, serverManager.rootDirectory, serverManager.maintenanceDirectory, serverManager);
        serverManager.serverListenerThread = serverListenerThread;

        serverManager.setServerOnNormalRunningMode();
        assertTrue(serverManager.serverListenerThread.getServerStatus());
    }
}