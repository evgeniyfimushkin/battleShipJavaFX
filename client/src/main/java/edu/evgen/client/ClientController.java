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
import java.util.Arrays;
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
        Platform.runLater(() -> {
                    if (clients != null)
                        clients.clear();
                    if (controller.getClass().equals(StartController.class)) {
                        ((StartController) controller).getClientListTextArea().clear();
                        clients.addAll((Collection<? extends String>) message.getList());
                        message.getList().forEach(id -> ((StartController) controller).getClientListTextArea().appendText((String) id + "\n"));
                    }
                });

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

    public void oponentIsReady(Message message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("OPPONENT IS READY!");
            alert.showAndWait();
        });
    }

    public void moveCommand() {
        Platform.runLater(() -> ((MainController)controller).getInfo().setText("You're moving"));
//        Arrays.stream(((MainController)controller)//убираем действия с наших кнопок
//                .getMyMainField()
//                .getButtonExtendeds())
//                .forEach(row -> Arrays.stream(row)
//                        .forEach(buttonExtended -> buttonExtended.getButton().setOnAction(event -> {})));
    }
    public void waitCommand() {
        Platform.runLater(() -> ((MainController)controller).getInfo().setText("Waiting for opponent's move"));
    }
}
