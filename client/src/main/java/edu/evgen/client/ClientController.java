package edu.evgen.client;

import edu.evgen.controllers.AbstractController;
import edu.evgen.controllers.MainController;
import edu.evgen.controllers.StartController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Slf4j

public class ClientController {
    private List<String> clients = new ArrayList<>();
    AbstractController controller;

    public ClientController() {
    }

    public ClientController(AbstractController controller) {
        this.controller = controller;
    }

    public void refreshClients(Message message) {
        if (clients != null)
            clients.clear();
        if (controller.getClass().equals(StartController.class)) {
            ((StartController) controller).getClientListTextArea().clear();
            clients.addAll((Collection<? extends String>) message.getList());
            message.getList().forEach(id -> ((StartController) controller).getClientListTextArea().appendText((String) id + "\n"));
        }
//        ((StartController) controller).getClientListTextArea().setText(message.getList().toString());
//        startController.getClientIdTextField().setText(clients.toString());

    }
@SneakyThrows
    public void printMyId(String id) {
        if (controller.getClass().equals(StartController.class)) {
            Platform.runLater(() ->((StartController)controller).getIdLabel().setText("My id: " + id));
        }
    }

    public void askNewGame(Message message) {
        ((StartController)controller).invitePlayer(message);
    }

    public void offerResponseRead(Message message) {
        if (message.getList().getLast().equals(false)){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Player don't want to play with you!");
                alert.showAndWait();
            });
        } else if (message.getList().getLast().equals(true)) {
            log.info("PLAYYYYYES");
            ((StartController)controller).gameBegining(message);
        }
    }
}
