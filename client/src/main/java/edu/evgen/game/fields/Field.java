package edu.evgen.game.fields;

import edu.evgen.controllers.AbstractController;
import edu.evgen.game.ship.ButtonExtended;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class Field {
    static final Integer size = 10;
    private ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];
    public void alert(String string) {
        log.info(string);
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }
}
