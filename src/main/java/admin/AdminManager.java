package admin;

import server.ServerManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public final class AdminManager extends Application {
    private ServerState serverState = new ServerState(0); //server is stopped
    private ServerManager serverManager = new ServerManager();

    public void initializeOptions() {
        serverState.initializeOptions();
    }

    public void handleOptions() {
        launch();
        int state = serverState.processServerState();
        while (state != -1) {
            switch (state) {
                case 0: {
                    serverManager.closeServer();
                    break;
                }
                case 1: {
                    serverManager.setHTMLFiles(serverState.getResourcesMap());
                    serverManager.setServerOnNormalRunningMode();
                    break;
                }
                case 2: {
                    serverManager.setServerOnMaintenanceMode();
                    break;
                }
                case 3: {
                    serverManager.setPort(serverState.getPort());
                    break;
                }
                case 4: {
                    serverManager.setRootDirectory(serverState.getRootDirectory());
                    serverManager.setHTMLFiles(serverState.getResourcesMap());
                    break;
                }
                case 5: {
                    serverManager.setMaintenanceDirectory(serverState.getMaintenanceDirectory());
                    break;
                }
                default: break;
            }
            state = getServerState().processServerState();
        }
        serverManager.closeServer();
        System.out.println("Program is closed successfully");
    }

    public ServerState getServerState() {
        return this.serverState;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
