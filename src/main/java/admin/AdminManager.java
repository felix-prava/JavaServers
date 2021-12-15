package admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.ServerManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class AdminManager extends Application {
    public static final int DEFAULT_PORT = 3000;
    private int port = DEFAULT_PORT;
    private String rootDirectory = "clientWebsite\\rootDirectory\\";
    private String maintenanceDirectory = "clientWebsite\\maintenanceDirectory\\";

    private static Stage primaryStage;
    private static Scene serverStoppedScene, normalServerScene, maintenanceServerScene;


    private ServerState serverState = new ServerState(0); //server is stopped
    private ServerManager serverManager = new ServerManager();

    public ServerManager getServerManager() {
        return this.serverManager;
    }

    public ServerState getServerState() {
        return this.serverState;
    }

    public void setPort(int portReceived) {
        this.port = portReceived;
        serverManager.setPort(portReceived);
    }

    public int getPort() {
        return this.port;
    }

    public void setRootDirectory(String rootDirectoryReceived) { this.rootDirectory = rootDirectoryReceived; }

    public void setMaintenanceDirectory(String maintenanceDirectoryReceived) {
        this.maintenanceDirectory = maintenanceDirectoryReceived;
    }

    public void initializeOptions() {
        serverState.initializeOptions();
    }

    public void handleOptions() {
        launch();
        serverManager.closeServer();
    }

    public void startServer() {
        serverState.updateResources();
        serverManager.closeServer();
        serverManager.setHTMLFiles(serverState.getResourcesMap());
        serverManager.setServerOnNormalRunningMode();
        primaryStage.setScene(normalServerScene);
    }

    public void setServerOnMaintenanceMode() {
        serverManager.setServerOnMaintenanceMode();
        primaryStage.setScene(maintenanceServerScene);
    }

    public void setServerOnNormalMode() {
        serverManager.setServerOnNormalRunningMode();
        primaryStage.setScene(normalServerScene);
    }

    public void stopServer() {
        serverManager.closeServer();
        primaryStage.setScene(serverStoppedScene);
    }

    public void updatePort() {
        TextField portField = (TextField) serverStoppedScene.lookup("#serverPortField");
        int newPort = Integer.parseInt(portField.getText());
        serverManager.setPort(newPort);
        this.port = newPort;

        Label portLabel = (Label) normalServerScene.lookup("#normalModePortDisplay");
        Label portLabel2 = (Label) maintenanceServerScene.lookup("#maintenanceModePortDisplay");
        portLabel.setText(Integer.toString(newPort));
        portLabel2.setText(Integer.toString(newPort));

        TextField textField1 = (TextField) maintenanceServerScene.lookup("#nonEditableTextField1");
        TextField textField2 = (TextField) normalServerScene.lookup("#normalModePortField");
        textField1.setPromptText(Integer.toString(newPort));
        textField2.setPromptText(Integer.toString(newPort));
        portField.setPromptText(Integer.toString(newPort));
        portField.setText("");
    }

    public void changeRootDirectory(boolean isFromServerStoppedScene) {
        TextField textField1 = (TextField) serverStoppedScene.lookup("#rootDirectoryField");
        TextField textField2 = (TextField) maintenanceServerScene.lookup("#rootDirectoryField");
        TextField textField3 = (TextField) normalServerScene.lookup("#normalModeRootDirectoryField");
        String newRootDirectory = isFromServerStoppedScene ? textField1.getText() : textField2.getText();
        if (!serverState.pathIsCorrect(newRootDirectory, true)) {
            // TO DO show error message
            System.out.println("ERROR");
            return;
        }
        if (!newRootDirectory.endsWith("\\")) {
            newRootDirectory += "\\";
        }
        textField1.setPromptText(newRootDirectory);
        textField2.setPromptText(newRootDirectory);
        textField1.setText("");
        textField2.setText("");
        textField3.setPromptText(newRootDirectory);
        this.rootDirectory = (newRootDirectory);
        serverManager.setRootDirectory(newRootDirectory);
        serverState.setRootDirectory(newRootDirectory);
        serverState.updateResources();
        serverManager.setHTMLFiles(serverState.getResourcesMap());
    }

    public void changeMaintenanceDirectory(boolean isFromServerStoppedScene) {
        if (serverState.pathIsCorrect("path", false)) {
            return;
        }
        System.out.println("TEST");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Java Servers");
        Parent rootStopped = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        Parent rootNormalMode = FXMLLoader.load(getClass().getResource("serverNormalMode.fxml"));
        Parent rootMaintenanceMode = FXMLLoader.load(getClass().getResource("serverMaintenanceMode.fxml"));
        serverStoppedScene = new Scene(rootStopped);
        normalServerScene = new Scene(rootNormalMode);
        maintenanceServerScene = new Scene(rootMaintenanceMode);
        primaryStage.setScene(serverStoppedScene);
        primaryStage.show();
    }
}
