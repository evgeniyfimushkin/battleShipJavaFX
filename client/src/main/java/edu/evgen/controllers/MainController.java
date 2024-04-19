package edu.evgen.controllers;

import edu.evgen.client.Client;
import edu.evgen.client.ClientController;
import edu.evgen.client.MessageMarker;
import edu.evgen.game.fields.EnemyField;
import edu.evgen.game.fields.Field;
import edu.evgen.game.fields.FieldType;
import edu.evgen.game.fields.MyField;
import edu.evgen.game.ship.ButtonExtended;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MainController extends AbstractController {
    @FXML
    GridPane myField, enemyField;
    @FXML
    Button readyButton, clearButton;
    @FXML
    public Label countShips, warnings, info;
    private ClientController clientController;
    private Client client;
    MyField myMainField;
    EnemyField enemyMainField;

    @FXML
    private void initialize() {
        myMainField = new MyField(this);
        enemyMainField = new EnemyField(this);
        clearButton.setDisable(false);
        readyButton.setDisable(false);
        clearButton.setOnAction(myMainField::clear);
        readyButton.setOnAction(myMainField::ready);
        fillGridPane(myField, 10, 10, FieldType.MY_FIELD,myMainField);
        fillGridPane(enemyField, 10, 10, FieldType.ENEMY_FIELD,enemyMainField);
    }

    //Создание кнопок в GridPane
    public void fillGridPane(GridPane gridPane, int width, int height, FieldType type, Field field) {
        Platform.runLater(() -> {
            info.setText("Set up your ships");
            warnings.setText("");
            gridPane.getChildren().clear(); // Очищаем GridPane перед заполнением
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Button button = new Button();
                    button.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE); // Устанавливаем кнопке максимальный размер
                    GridPane.setFillHeight(button, true); // Разрешаем кнопке заполнять всю доступную высоту ячейки
                    GridPane.setFillWidth(button, true); // Разрешаем кнопке заполнять всю доступную ширину ячейки
                    button.setStyle(" -fx-background-radius: 0;");
                    gridPane.add(button, col, row); // Добавляем кнопку в GridPane
                    ButtonExtended buttonExtended = new ButtonExtended(button, col, row, type);
                    if (field.getClass().equals(MyField.class)) {
                        buttonExtended.getButton().setOnAction(event -> myMainField.buttonCreateShipsAction(buttonExtended));
                        myMainField.buttonExtendedsAdd(buttonExtended);
                    } else if (field.getClass().equals(EnemyField.class)) {
                        buttonExtended.getButton().setOnAction(event -> enemyMainField.buttonEnemyEnemy(buttonExtended));
                        enemyMainField.buttonExtendedsAdd(buttonExtended);
                    }
                }
            }
        });
    }
    public void restart(){
        initialize();
        client.setStatus(MessageMarker.STARTGAMING);
    }
}
