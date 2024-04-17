package edu.evgen.game;

import edu.evgen.game.fields.EnemyField;
import edu.evgen.game.fields.MyField;
import javafx.event.ActionEvent;
import lombok.*;

@Getter
@Setter
public class GameController {
    private Boolean active;
    private MyField myField;
    private EnemyField enemyField;

    public GameController(MyField myField, EnemyField enemyField) {
        this.myField = myField;
        this.enemyField = enemyField;
        this.active = false;
    }

    private void start() {
        active = true;
    }

    private void end() {
        active = false;
    }
}
