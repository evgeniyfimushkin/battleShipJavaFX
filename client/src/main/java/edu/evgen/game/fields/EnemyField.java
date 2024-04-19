package edu.evgen.game.fields;

import edu.evgen.controllers.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@Slf4j
public class EnemyField extends Field {
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<Ship> hittedShips = new ArrayList<>();
    private MainController controller;

    public EnemyField(MainController controller) {
        this.controller = controller;
    }
    public void buttonShootAction(ButtonExtended buttonExtended) {
            shot(buttonExtended);
        log.info(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }

    private void shot(ButtonExtended buttonExtended) {
        buttonExtended.getButton().setText("*");
        buttonExtended.getButton().setDisable(true);
        controller.getClient().sendShootRequest(buttonExtended);
    }

    public void buttonEnemyEnemy(ButtonExtended buttonExtended) {
        switch (controller.getClient().getStatus()) {
            case MOVE -> buttonShootAction(buttonExtended);
        }
    }

    public void shotResponseHandler(Integer x, Integer y, Integer status) {
        Button button = buttonExtendeds[x][y].getButton();
        Platform.runLater(() -> {
            switch (status) {
                case 0://мимо

                    break;
                case 1://попал
                    log.info("Hitted");

                    button.setText("x");
                    button.setStyle(" -fx-background-radius: 0; -fx-background-color: #FF4D00");

                    break;
                case 2://уничтожил
                    log.info("Killed");
                    log.info("Hitted");

                    button.setText("x");
                    button.setStyle(" -fx-background-radius: 0; -fx-background-color: #7B001C");
                    break;

            }
        });
    }
}
