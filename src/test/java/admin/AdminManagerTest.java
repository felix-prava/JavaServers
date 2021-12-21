package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ServerManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest extends Application {

    private AdminManager adminManager;

    @Override
    public void start(Stage primaryStage) throws IOException {
        adminManager.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Java Servers Test");
        Parent rootStopped = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        Parent rootNormalMode = FXMLLoader.load(getClass().getResource("serverNormalMode.fxml"));
        Parent rootMaintenanceMode = FXMLLoader.load(getClass().getResource("serverMaintenanceMode.fxml"));
        AdminManager.setServerStoppedScene( new Scene(rootStopped));
        AdminManager.setMaintenanceServerScene(new Scene(rootMaintenanceMode));
        AdminManager.setNormalServerScene(new Scene(rootNormalMode));
        adminManager.restoreErrorMessages();
        primaryStage.setScene(AdminManager.getServerStoppedScene());
        primaryStage.show();
    }

    @BeforeEach
    void setup() {
        adminManager = new AdminManager();
    }

    @Test
    void startProgramTest() {
        adminManager.startProgram();
    }

    @Test
    void startServerTest() {
    }

    @Test
    void setServerOnMaintenanceModeTest() {
    }

    @Test
    void setServerOnNormalModeTest() {
    }

    @Test
    void stopServerTest() {
    }

    @Test
    void updatePortTest() {
    }

    @Test
    void changeRootDirectoryTest() {
    }

    @Test
    void changeMaintenanceDirectoryTest() {
    }

    @Test
    void startTest() {
    }

    @Test
    void restoreErrorMessagesTest() {
    }

    @Test
    void displayErrorMessageTest() {
    }

    @Test
    void portIsValidTest() {
    }
}