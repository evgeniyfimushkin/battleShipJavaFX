package edu.evgen.game.ship;

import edu.evgen.game.fields.EnemyField;
import edu.evgen.game.fields.FieldType;
import edu.evgen.game.fields.MyField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import lombok.Data;

@Data
public class ButtonExtended {
    public static MyField myField;
    private Button button;
    private Integer x;
    private Integer y;
    private FieldType fieldType;
    private Boolean activated;
    private Boolean hited;

    public ButtonExtended(Button button, Integer x, Integer y, FieldType fieldType) {
        this.button = button;
        this.x = x;
        this.y = y;
        this.fieldType = fieldType;
        this.activated = false;
        this.hited = false;
        this.setOnAction();
    }

    private void setOnAction() {
        switch (fieldType) {
            case MY_FIELD -> myField.buttonExtendedRegister(this);
            case ENEMY_FIELD -> EnemyField.buttonExtendedRegister(this);
        }
    }
    public void setShot(){
        button.setText("*");
    }
    private void setOnActionEnemyField(ActionEvent event) {
        setShot();
        System.out.println(x + " " + y + "en");
    }
}
