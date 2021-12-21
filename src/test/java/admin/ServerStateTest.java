package admin;

import org.junit.jupiter.api.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerStateTest {
    private ServerState serverState;

    @BeforeEach
    public void setup() {
        serverState = new ServerState();
    }

    @Test
    @DisplayName("updateResources function should clear the resourcesMap HashMap when the path does not exist")
    void updateResourcesTest() {
        serverState.setRootDirectory("\\should_not_exist");
        serverState.updateResources();
        assertEquals(0, serverState.getResourcesMap().size());
    }

    @Test
    @DisplayName("resourcesMap HashMap should have one element after creating a html file in the root directory")
    void updateResourcesTest2() {
        String homePath = System.getProperty("user.home");
        serverState.setRootDirectory(homePath);
        serverState.updateResources();
        assertEquals(0, serverState.getResourcesMap().size());
        // TODO
        // Create a html file in homePath folder
        // serverState.updateResources();
        // assertEquals(1, serverState.getResourcesMap().size());
        // Delete file
    }

    @Test
    @DisplayName("pathIsCorrect function should return false when the path does not exist")
    void pathIsCorrectTest() {
        String answer1 = serverState.pathIsCorrect("\\should_not_exist", true);
        String answer2 = serverState.pathIsCorrect("\\should_not_exist", false);
        assertNotEquals(answer1, "OK");
        assertNotEquals(answer2, "OK");
        assertEquals(answer1, "This path does not correspond to a directory\n");
        assertEquals(answer2, "This path does not correspond to a directory\n");
    }

    /*
    @Test
    @DisplayName("pathIsCorrect function should return true after creating a folder in the home directory")
    void pathIsCorrectTest2() {
        String homePath = System.getProperty("user.home");
        // TODO
        // Create a folder with a pageNotFound.html file in homePath
        assertEquals(serverState.pathIsCorrect(homePath, true), "OK");
        // Delete file and folder
    }
    */

    @Test
    @DisplayName("Test if isNumber function returns the correct boolean")
    void isNumberTest() {
        String shouldReturnTrue = "2021";
        assertTrue(serverState.isNumber(shouldReturnTrue));
        String shouldReturnFalse = "2021.20";
        assertFalse(serverState.isNumber(shouldReturnFalse));
        shouldReturnFalse = "ABC";
        assertFalse(serverState.isNumber(shouldReturnFalse));
    }

    @Test
    void getResourcesMapTest() {
        HashMap<String, String> testMap = new HashMap<>();
        assertEquals(testMap, serverState.getResourcesMap());
        serverState.getResourcesMap().put("keyA", "valueA");
        testMap.put("keyA", "valueA");
        assertEquals(testMap.get("keyA"), serverState.getResourcesMap().get("keyA"));
        assertEquals(testMap.get("noKey"), serverState.getResourcesMap().get("anotherKey"));
    }

    @Test
    public void setMaintenanceDirectoryTest() {
        serverState.setMaintenanceDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverState.getMaintenanceDirectory());
        assertNotEquals("\\home", serverState.getMaintenanceDirectory());
    }

    @Test
    public void setRootDirectoryTest() {
        serverState.setRootDirectory("\\should_not_exist");
        assertEquals("\\should_not_exist", serverState.getRootDirectory());
        assertNotEquals("\\home", serverState.getRootDirectory());
    }

    @Test
    public void setPortTest() {
        serverState.setPort(8080);
        assertEquals(8080, serverState.getPort());
        assertNotEquals(3000, serverState.getPort());
    }

    @Test
    public void getMaintenanceDirectoryTest() {
        assertEquals("clientWebsite\\maintenanceDirectory\\",
                serverState.getMaintenanceDirectory());
        serverState.setMaintenanceDirectory("\\");
        assertNotEquals("Home", serverState.getMaintenanceDirectory());
    }

    @Test
    public void getRootDirectoryTest() {
        assertEquals("clientWebsite\\rootDirectory\\",
                serverState.getRootDirectory());
        serverState.setRootDirectory("\\");
        assertNotEquals("Home", serverState.getRootDirectory());
    }

    @Test
    public void getPortTest() {
        assertEquals(3000, serverState.getPort());
        serverState.setPort(3001);
        assertNotEquals(20000, serverState.getPort());
    }
}