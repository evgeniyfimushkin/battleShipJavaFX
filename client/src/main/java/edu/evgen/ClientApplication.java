package edu.evgen;

import edu.evgen.controllers.ConnectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConnectionScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        ConnectionController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("FXML Example");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}