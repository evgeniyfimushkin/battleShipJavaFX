package edu.evgen.controllers;

import edu.evgen.client.Client;
import edu.evgen.client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class StartController extends AbstractController {
    @FXML
    Button playButton, refreshButton;
    @FXML
    TextArea clientListTextArea;
    @FXML
    TextField clientIdTextField;

    @FXML
    private void initialize() {
        clientListTextArea.setEditable(false);
        playButton.setOnAction(this::playTry);
        refreshButton.setOnAction(this::refreshPlayers);
    }

    private void refreshPlayers(ActionEvent event) {
        client.getSessionsRequest();
    }

    public void setClient(Client client){
        this.client = client;
        client.setClientController(new ClientController(this));
    }
    private void playTry(ActionEvent event) {
    }
}
