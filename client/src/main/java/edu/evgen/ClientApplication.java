package edu.evgen;

import edu.evgen.client.Client;
import edu.evgen.client.DTO.ClientDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        MainController controller = loader.getController();
        primaryStage.setTitle("FXML Example");
        primaryStage.setResizable(false);
        primaryStage.show();
        Client client = new Client("localhost",8809);
    }
}
