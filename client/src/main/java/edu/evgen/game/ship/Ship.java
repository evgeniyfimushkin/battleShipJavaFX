package edu.evgen.game.ship;

import edu.evgen.ButtonExtended;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Ship {
    private Size size;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<ButtonExtended> buttons = new ArrayList<>();
    private Boolean alive;

    public Ship(Size size, ArrayList<Point> points) {
        if (size.getSize() == points.size()) {
            alive = true;
            this.size = size;
            this.points = points;
            points.forEach(point -> point.setIsHit(false));
        }
    }

}
