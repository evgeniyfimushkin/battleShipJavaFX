package edu.evgen.game.fields;

import edu.evgen.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Point;
import edu.evgen.game.ship.Ship;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class EnemyField extends Field {
    private ArrayList<Shot> shots = new ArrayList<>();
    private ArrayList<Ship> hittedShips = new ArrayList<>();
}
