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
    Client client;
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
        Stage currentStage = (Stage) stage.getScene().getWindow(); // замените yourCurrentNode на любой узел в вашем текущем окне

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
            StartController controller = new StartController();
            loader.setController(controller);

            // Загружаем новый FXML файл
            Parent root = loader.load();

            // Создаем новую сцену
            Scene scene = new Scene(root);

            // Устанавливаем новую сцену на текущем Stage
            currentStage.setScene(scene);
            currentStage.show();

            // Закрываем текущее окно
//            currentStage.close();
        } catch (IOException e) {
            log.info("ERROR");
        }
    }

}
