package admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private static Scene serverStoppedScene;


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

    public void startServer() throws IOException {
        System.out.println("AM AJUNS AICI");
        serverState.updateResources();
        serverManager.closeServer();
        serverManager.setHTMLFiles(serverState.getResourcesMap());
        serverManager.setServerOnNormalRunningMode();
        Parent rootNormal = FXMLLoader.load(getClass().getResource("serverNormalMode.fxml"));
        Scene normalModeScene = new Scene(rootNormal);
        primaryStage.setScene(normalModeScene);
        //primaryStage.show();
    }

    public void stopServer() throws IOException {
        serverManager.closeServer();
        Parent rootStopped = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        //Scene stoppedServerScene = new Scene(rootStopped);
        primaryStage.setScene(serverStoppedScene);
        //primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Hello World!");

        //Parent rootNormal = FXMLLoader.load(getClass().getResource("serverNormalMode.fxml"));
        Parent rootStopped = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        serverStoppedScene = new Scene(rootStopped);
        //Scene normalModeScene = new Scene(rootNormal);
        primaryStage.setScene(serverStoppedScene);
        primaryStage.show();
        /*Button btn = (Button) scene.lookup("#updatePort");
        btn.setOnAction(event -> {
            System.out.println("Hello World!");
            TextField portField = (TextField) scene.lookup("#serverPortField");
            serverManager.setPort(Integer.parseInt(portField.getText()));
            serverState.updateResources();
            serverManager.closeServer();
            serverManager.setHTMLFiles(serverState.getResourcesMap());
            serverManager.setServerOnNormalRunningMode();
            //primaryStage.setScene(normalModeScene);
            //primaryStage.show();
        });*/

        /*
        Parent root = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("The Wolves of Vasile Parvan Street");
        primaryStage.setScene(scene);
        primaryStage.show();
         */
    }

    public void serverStoppedUpdatePort() throws IOException {
        Parent rootStopped = FXMLLoader.load(getClass().getResource("serverStopped.fxml"));
        //Scene serverStoppedScene = new Scene(rootStopped);
        TextField portField = (TextField) serverStoppedScene.lookup("#serverPortField");
        System.out.println("A " + portField.getText());
        serverManager.setPort(Integer.parseInt(portField.getText()));
    }
}
