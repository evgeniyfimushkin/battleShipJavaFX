package edu.evgen.game.fields;

import edu.evgen.controllers.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class EnemyField extends Field {
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<Ship> hittedShips = new ArrayList<>();
    private ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];

    private MainController controller;

    public EnemyField(MainController controller) {
        this.controller = controller;

    }
    public void buttonShootAction(ButtonExtended buttonExtended) {
        if (buttonExtended.getActivated()) {
            buttonExtended.getButton();
        } else {
            shot();
        }
        System.out.println(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }

    private void shot() {
        System.out.println("SHOOT");
    }

    public void buttonEnemyEnemy(ButtonExtended buttonExtended) {
        buttonShootAction(buttonExtended);
    }
}
