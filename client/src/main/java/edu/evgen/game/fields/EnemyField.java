package edu.evgen.game.fields;

import edu.evgen.controllers.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Ship;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EnemyField extends Field {
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<Ship> hittedShips = new ArrayList<>();
    private ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];

    private MainController controller;

    public EnemyField(MainController controller) {
        this.controller = controller;

    }
    public static void buttonExtendedRegister(ButtonExtended buttonExtended) {
        buttonExtended.getButton().setOnAction(event -> buttonAction(buttonExtended));
//        buttonExtendeds.add(buttonExtended);
    }

    private static void buttonAction(ButtonExtended buttonExtended) {
//        System.out.println(buttonExtendeds.size());
        System.out.println(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }
}
