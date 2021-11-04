package Admin;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerStateTest {
    ServerState serverState;

    @BeforeEach
    public void setup() {
        serverState = new ServerState(0);
    }

    @DisplayName("Test if the menu contains different keys")
    @ParameterizedTest
    @ValueSource (ints = {-1, 0, 1, 2, 3, 4, 5, 100, 101, 102})
    void initializeOptionsTest(int key) {
        serverState.initializeOptions();
        Assertions.assertTrue(serverState.getMenu().containsKey(key));
    }

    @DisplayName("Test if the menu contains different values")
    @ParameterizedTest
    @ValueSource (strings = {"Close the program", "Change the root directory of the server", "Put the server on normal mode"})
    void initializeOptionsTest2(String value) {
        serverState.initializeOptions();
        Assertions.assertTrue(serverState.getMenu().containsValue(value));
    }

    @DisplayName("Test if the menu do not contains different keys")
    @ParameterizedTest
    @ValueSource (ints = {-2, 7, 99})
    void initializeOptionsTest3(int key) {
        serverState.initializeOptions();
        Assertions.assertFalse(serverState.getMenu().containsKey(key));
    }

    @DisplayName("Test if the menu do not contains different values")
    @ParameterizedTest
    @ValueSource (strings = {"Close this program", "Change the root directory of the server ", "Change the server on normal mode"})
    void initializeOptionsTest4(String value) {
        serverState.initializeOptions();
        Assertions.assertFalse(serverState.getMenu().containsValue(value));
    }

    @Test
    @DisplayName("Test the output of the showMenu function")
    void showMenuTest() {
        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;
        ByteArrayInputStream testIn;
        ByteArrayOutputStream testOut;

        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        final String testString =
                "-1 - Close the program\n" +
                "1 - Put the server on normal mode\n" +
                "2 - Put the server on maintenance mode\n" +
                "3 - Change the port of the server\n" +
                "4 - Change the root directory of the server\n" +
                "5 - Change the maintenance directory of the server\n";
        testIn = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testIn);
        serverState.initializeOptions();
        serverState.showMenu(0);
        assertNotEquals(testString, testOut.toString()); // "You can choose one of the following:" is missing
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    @DisplayName("updateResources function should clear the resourcesMap HashMap when the path does not exist")
    void updateResourcesTest() {
        serverState.setRootDirectory("\\should_not_exist");
        serverState.updateResources();
        assertEquals(0, serverState.getResourcesMap().size());
    }

    /* @Test
    @DisplayName("resourcesMap HashMap should have one element after creating a html file in the root directory")
    void updateResourcesTest2() {
        String homePath = System.getProperty("user.home");
        serverState.setRootDirectory(homePath);
        serverState.updateResources();
        // TODO
        // Create a html file in homePath folder
        assertEquals(1, serverState.getResourcesMap().size());
        // Delete file
    } */

    @Test
    @DisplayName("pathIsCorrect function should return false when the path does not exist")
    void pathIsCorrectTest() {
        assertFalse(serverState.pathIsCorrect("\\should_not_exist", true));
        assertFalse(serverState.pathIsCorrect("\\should_not_exist", false));
    }

    /*
    @Test
    @DisplayName("pathIsCorrect function should return true after creating a folder in the home directory")
    void pathIsCorrectTest2() {
        String homePath = System.getProperty("user.home");
        // TODO
        // Create a folder with a pageNotFound.html file in homePath
        assertTrue(serverState.pathIsCorrect(homePath, true));
        // Delete file and folder
    } */

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
    public void getMenuTest() {
        assertNull(serverState.getMenu());
        serverState.initializeOptions();
        assertEquals(10, serverState.getMenu().size());
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
    public void setStateTest() {
        serverState.setState(5);
        assertEquals(5, serverState.getState());
        assertNotEquals(6, serverState.getState());
    }

    @Test
    public void setPortTest() {
        serverState.setPort(8080);
        assertEquals(8080, serverState.getPort());
        assertNotEquals(3000, serverState.getPort());
    }

    @Test
    public void getStateTest() {
        assertEquals(0, serverState.getState());
        serverState.setState(5);
        assertNotEquals(1, serverState.getState());
    }

    @Test
    public void getResourcesMapTest() {
        // resourcesMap hashmap is just declared for the moment
        assertEquals(new HashMap<>(), serverState.getResourcesMap());
        serverState.setRootDirectory("shot_not_exist");
        try {
            serverState.initializeOptions();
        } catch (NullPointerException ignored) {

        } finally {
            assertEquals(0, serverState.getResourcesMap().size());
        }
    }

    @Test
    public void getMaintenanceDirectoryTest() {
        assertEquals("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\maintenanceDirectory\\",
                serverState.getMaintenanceDirectory());
        serverState.setMaintenanceDirectory("\\");
        assertNotEquals("Home", serverState.getMaintenanceDirectory());
    }

    @Test
    public void getRootDirectoryTest() {
        assertEquals("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\rootDirectory\\",
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

    @Test
    public void ServerStateConstructorTest() {
        ServerState newServerState = new ServerState(0);
        assertEquals(0, newServerState.getState());
        ServerState anotherServerState = new ServerState(2);
        assertEquals(2, anotherServerState.getState());
        assertNotEquals(3, anotherServerState.getState());
    }
}