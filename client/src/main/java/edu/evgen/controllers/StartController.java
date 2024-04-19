package edu.evgen.controllers;

import edu.evgen.client.Client;
import edu.evgen.client.ClientController;
import edu.evgen.client.Message;
import edu.evgen.client.MessageMarker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Getter
@Slf4j
public class StartController extends AbstractController {
    @FXML
    Button playButton, refreshButton;
    @FXML
    TextArea clientListTextArea;
    @FXML
    TextField clientIdTextField;
    @FXML
    Label idLabel;

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
        client.sendEmptyMessage(MessageMarker.OFFERREQUEST,clientIdTextField.getText());
    }

    public void invitePlayer(Message message) {
        Platform.runLater(() -> {
            String playerId = message.getSender();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Game Invitation");
            alert.setHeaderText("The player " + playerId + " invites you to the game.");
            alert.setContentText("Do you want to accept the invitation?");

            // Добавляем кнопки "Yes" и "No" и привязываем к ним действия
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Обработка нажатия кнопок
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    // Действие при нажатии "Yes"
                    gameBegining(message);
                    acceptInvitation(message);
                } else if (buttonType == buttonTypeNo) {
                    // Действие при нажатии "No"
                    declineInvitation(message);
                }
            });
        });
    }

    private void acceptInvitation(Message message) {
        List<Boolean> list = new LinkedList<>();
        list.add(true);
        client.sendNotEmptyMessage(new Message(MessageMarker.OFFERRESPONSE,client.id,message.getSender(),list));
        // Ваш код для принятия приглашения
    }

    private void declineInvitation(Message message) {
        List<Boolean> list = new LinkedList<>();
        list.add(false);
        client.sendNotEmptyMessage(new Message(MessageMarker.OFFERRESPONSE,client.id,message.getSender(),list));
        // Ваш код для принятия приглашения
    }

    public void gameBegining(Message message) {
        client.setOpponent(message.getSender());
        client.sendNotEmptyMessage(new Message(MessageMarker.STARTGAMING,message.getSender(), message.getRecipient(), null));
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
                Parent root = loader.load();
                MainController controller = loader.getController(); // Получаем контроллер после загрузки FXML
                controller.setClient(client);
                controller.setStage(stage); // Передаем текущий Stage в контроллер
                controller.getClient().getClientController().setController(controller);
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("ERROR");
            }
        });

    }
}
