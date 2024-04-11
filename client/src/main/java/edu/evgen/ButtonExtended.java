package edu.evgen;

import edu.evgen.game.fields.FieldType;
import edu.evgen.game.fields.MyField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import lombok.Data;

@Data
public class ButtonExtended {
    Button button;
    private Integer x;
    private Integer y;
    private FieldType fieldType;

    public ButtonExtended(Button button, Integer x, Integer y, FieldType fieldType) {
        this.button = button;
        this.x = x;
        this.y = y;
        this.fieldType = fieldType;
        this.setOnAction();
    }

    private void setOnAction() {
        switch (fieldType) {
            case MY_FIELD -> button.setOnAction(this::setOnActionMyField);
            case ENEMY_FIELD -> button.setOnAction(this::setOnActionEnemyField);
        }
    }
    public void setShot(){
        button.setText("*");
    }
    private void setOnActionMyField(ActionEvent rootEvent) {
        MyField.clickHandler(this);
    }
    private void setOnActionEnemyField(ActionEvent event) {
        setShot();
        System.out.println(x + " " + y + "en");
    }
}
