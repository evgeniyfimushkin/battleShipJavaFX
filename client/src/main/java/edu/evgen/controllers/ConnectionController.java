package edu.evgen.controllers;

import edu.evgen.client.Client;
import edu.evgen.client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ConnectionController extends AbstractController {
    ClientController clientController;
    @FXML
    TextField adressTextField;
    @FXML
    Button playButton;
    String address;
    Integer port;

    @FXML
    private void initialize() {
        playButton.setOnAction(this::connectionTry);
    }

    private void connectionTry(ActionEvent event) {
        String[] tokens = adressTextField.getText().split(":");
        address = tokens[0];
        try {
            port = Integer.parseInt(tokens[1]);
            try {
                clientController = new ClientController();
                client = new Client(address, port, clientController);
                log.info("Connection Success {}:{}", address, port);
                connect();
            } catch (Throwable e) {

            }
        } catch (Throwable e) {
            alert("Bad Value!");
        }


    }

    private void connect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartScene.fxml"));
            Parent root = loader.load();
            StartController controller = loader.getController(); // Получаем контроллер после загрузки FXML
            controller.setClient(client);
            controller.setStage(stage); // Передаем текущий Stage в контроллер
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("ERROR");
        }
    }

}
