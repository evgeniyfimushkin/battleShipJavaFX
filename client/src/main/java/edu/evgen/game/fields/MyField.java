package edu.evgen.game.fields;

import edu.evgen.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Point;
import edu.evgen.game.ship.Ship;
import edu.evgen.game.ship.Size;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MyField extends Field {
    private static ArrayList<Ship> ships = new ArrayList<>();
    private static ArrayList<Shot> shots = new ArrayList<>();
    public static void clickHandler(ButtonExtended buttonExtended){
        System.out.println(buttonExtended.getX() + " " + buttonExtended.getY() + "my");
        buttonExtended.getButton().setStyle("-fx-background-color:#576780;");
        ArrayList<Point> temp = new ArrayList<>();
        temp.add(new Point(buttonExtended.getX(),buttonExtended.getY()));
        addShip(Size.ONE,temp);
    }
    private static void addShip(Size size, ArrayList<Point> points){
        ships.add(new Ship(size,points));
        System.out.println("ship creating");
    }
    public static void click(Integer x, Integer y) {

    }
}