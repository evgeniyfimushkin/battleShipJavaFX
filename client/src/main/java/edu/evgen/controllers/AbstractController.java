package edu.evgen.controllers;

import edu.evgen.client.Client;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public abstract class AbstractController implements IAlertable{
    Stage stage;
    Client client;
    @Override
    public void alert(String string) {
        log.info(string);
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }
}
