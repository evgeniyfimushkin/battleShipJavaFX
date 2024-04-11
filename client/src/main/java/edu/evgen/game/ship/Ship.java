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

    public Ship(ButtonExtended buttonExtended) {
            alive = true;
            this.size = 1;
            buttonExtendeds.add(buttonExtended);
            buttonExtended.setActivated(true);
            buttonExtended.getButton().setStyle("-fx-background-color: grey;");
    }

    public void extendShip(ButtonExtended buttonExtended) {
        if (size > 0 && size < 4) {
            System.out.println("extend" + size + " " + MyField.getShips().stream().filter(ship -> ship.size == size).count());
            if ((size == 3) && (MyField.getShips().stream().filter(ship -> ship.size == 4).count() >= 1)){
                System.out.println("ASDASDASD");
                return;
            }
            else if ((size == 2) && (MyField.getShips().stream().filter(ship -> ship.size == 3).count() >= 2)){
                return;
            }
            else if ((size == 1) && (MyField.getShips().stream().filter(ship -> ship.size == 2).count() >= 3)){
                return;
            }
            size++;
            buttonExtendeds.add(buttonExtended);
            buttonExtended.setActivated(true);
            buttonExtended.getButton().setStyle("-fx-background-color: grey; -fx-background-radius: 0;");
        }
    }

}
