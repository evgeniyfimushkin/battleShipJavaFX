package edu.evgen.game.fields;

import edu.evgen.controllers.AbstractController;
import edu.evgen.game.ship.ButtonExtended;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class Field {
    static final Integer size = 10;
    protected ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];

    public void alert(String string) {
        log.info(string);
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public void setAllButtonsAction(EventHandler<ActionEvent> function) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttonExtendeds[row][col].getButton().setOnAction(function);
            }
        }
    }
    public void buttonExtendedsAdd(ButtonExtended buttonExtended) {
        Integer x = buttonExtended.getX();
        Integer y = buttonExtended.getY();
        buttonExtendeds[x][y] = buttonExtended;
    }

}
