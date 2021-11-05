package Admin;

import Server.ServerManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminManagerTest {
    private AdminManager adminManager;

    @BeforeEach
    public void setup() {
        adminManager = new AdminManager();
    }

    @DisplayName("Test if the menu contains different keys")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5, 100, 101, 102})
    void initializeOptionsTest(int key) {
        adminManager.initializeOptions();
        ServerState serverState = adminManager.getServerState();
        Assertions.assertTrue(serverState.getMenu().containsKey(key));
    }

    @DisplayName("Test if the menu contains different values")
    @ParameterizedTest
    @ValueSource (strings = {"Close the program", "Change the root directory of the server", "Put the server on normal mode"})
    void initializeOptionsTest2(String value) {
        adminManager.initializeOptions();
        ServerState serverState = adminManager.getServerState();
        Assertions.assertTrue(serverState.getMenu().containsValue(value));
    }

    @DisplayName("Test if the menu do not contains different keys")
    @ParameterizedTest
    @ValueSource (ints = {-2, 7, 99})
    void initializeOptionsTest3(int key) {
        adminManager.initializeOptions();
        ServerState serverState = adminManager.getServerState();
        Assertions.assertFalse(serverState.getMenu().containsKey(key));
    }

    @DisplayName("Test if the menu do not contains different values")
    @ParameterizedTest
    @ValueSource (strings = {"Close this program", "Change the root directory of the server ", "Change the server on normal mode"})
    void initializeOptionsTest4(String value) {
        adminManager.initializeOptions();
        ServerState serverState = adminManager.getServerState();
        Assertions.assertFalse(serverState.getMenu().containsValue(value));
    }
}