package edu.evgen.game.ship;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Ship {
    private Integer size;
    private ArrayList<ButtonExtended> buttonExtendeds = new ArrayList<>();
    private Boolean alive;

    public Ship(ButtonExtended buttonExtended) {
        alive = true;
        this.size = 1;
        buttonExtendeds.add(buttonExtended);
        buttonExtended.setActivated(true);
        buttonExtended.getButton().setStyle("-fx-background-color: grey;");
    }

    public void extendShip(ButtonExtended buttonExtended) {
        if (size > 0 && size < 4) {
            System.out.println("extend");
            size++;
            buttonExtendeds.add(buttonExtended);
            buttonExtended.setActivated(true);
            buttonExtended.getButton().setStyle("-fx-background-color: grey;");
        }
    }

}
