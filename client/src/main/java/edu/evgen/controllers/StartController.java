package edu.evgen.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StartController extends AbstractController {
    @FXML
    Button playButton, refreshButton;
    @FXML
    TextArea clientListTextArea;
    @FXML
    TextField clientIdTextField;
}
