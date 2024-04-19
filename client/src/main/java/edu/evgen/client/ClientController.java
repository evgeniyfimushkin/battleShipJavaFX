package edu.evgen.client;

import edu.evgen.controllers.AbstractController;
import edu.evgen.controllers.MainController;
import edu.evgen.controllers.StartController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
            if (!(controller.getClient().getId() == null))
                controller.getClient().getClientController().printMyId(controller.getClient().getId());
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
            Platform.runLater(() -> ((StartController) controller).getIdLabel().setText("My id: " + id));
        }
    }

    public void askNewGame(Message message) {
        ((StartController) controller).invitePlayer(message);
    }

    public void offerResponseRead(Message message) {
        if (message.getList().getLast().equals(false)) {
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
            ((StartController) controller).gameBegining(message);
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
        Platform.runLater(() -> ((MainController) controller).getInfo().setText("You're moving"));
//        Arrays.stream(((MainController)controller)//убираем действия с наших кнопок
//                .getMyMainField()
//                .getButtonExtendeds())
//                .forEach(row -> Arrays.stream(row)
//                        .forEach(buttonExtended -> buttonExtended.getButton().setOnAction(event -> {})));
    }

    public void waitCommand() {
        Platform.runLater(() -> ((MainController) controller).getInfo().setText("Waiting for opponent's move"));
    }

    public void shotRequestHandler(Message message) {
        Integer shotX, shotY;
        shotX = (Integer) message.getList().getFirst();
        shotY = (Integer) message.getList().getLast();
//        ((MainController)controller).getMyMainField().setHit(shotX, shotY);
        controller.getClient()
                .sendShootResponse(message, ((MainController) controller).getMyMainField().setHit(shotX, shotY));
    }

    public void shotResponseHandler(Message message) {
        Integer x = (Integer)message.getList().getFirst();
        Integer y = (Integer)message.getList().get(1);
        Integer status = (Integer) message.getList().getLast();
        ((MainController)controller).getEnemyMainField().shotResponseHandler(x,y,status);
    }

    public void winHandler(Message message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You Win! Want to play again?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if (result == ButtonType.YES) {
                controller.getClient().restartRequest();
            }else if (result == ButtonType.NO){
                Platform.runLater(() -> {
                    ((MainController)controller).getReadyButton().setDisable(false);
                    ((MainController)controller).getReadyButton().setText("Back To Lobby");
                    ((MainController)controller).getReadyButton().setOnAction(((MainController)controller)::backToLobby);
                });
            }
        });
    }

    public void restartHandler(Message message) {
        ((MainController)controller).restart();
    }
}
