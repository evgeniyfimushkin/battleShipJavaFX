package edu.evgen.game.ship;

import edu.evgen.game.fields.MyField;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
public class Ship {
    private Integer size;
    private ArrayList<ButtonExtended> buttonExtendeds = new ArrayList<>();
    private Boolean alive;
    public static MyField myField;

    public Ship(ButtonExtended buttonExtended) {
            alive = true;
            this.size = 1;
            buttonExtendeds.add(buttonExtended);
            buttonExtended.setActivated(true);
            buttonExtended.getButton().setStyle("-fx-background-color: grey;");
            buttonExtended.getButton().setText("");
    }
    }
