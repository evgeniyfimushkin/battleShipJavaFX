package edu.evgen.game.fields;

import edu.evgen.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MyField extends Field {
    private static ArrayList<Ship> ships = new ArrayList<>();
    private static ArrayList<Shot> shots = new ArrayList<>();
    private static final ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];
    private static MainController controller;

    public static void setController(MainController mainController) {
        controller = mainController;
    }

    public static void buttonExtendedRegister(ButtonExtended buttonExtended) {
        buttonExtended.getButton().setOnAction(event -> buttonAction(buttonExtended));
        buttonExtendedsAdd(buttonExtended);
    }

    private static void buttonExtendedsAdd(ButtonExtended buttonExtended) {
        Integer x = buttonExtended.getX();
        Integer y = buttonExtended.getY();
        buttonExtendeds[x][y] = buttonExtended;
    }

    private static void buttonAction(ButtonExtended buttonExtended) {
        if (buttonExtended.getActivated()) {
            buttonExtended.setActivated(false);
            buttonExtended.getButton().setStyle("");
        } else {
            createShip(buttonExtended);
            Platform.runLater(() -> controller.countShips.setText(String.valueOf(ships.size())));
        }
        System.out.println(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }

    private static void createShip(ButtonExtended buttonExtended) {
        Integer x = buttonExtended.getX();
        Integer y = buttonExtended.getY();
        if ((x > 0 && y > 0) && (x < 9 && y < 9)) {//отсекаем края
            if ((!buttonExtendeds[x + 1][y + 1].getActivated()) &&
                    (!buttonExtendeds[x - 1][y + 1].getActivated()) &&
                    (!buttonExtendeds[x + 1][y - 1].getActivated()) &&
                    (!buttonExtendeds[x - 1][y - 1].getActivated())) {
                if ((!(buttonExtendeds[x][y + 1].getActivated() &&//отсекаем углы
                        buttonExtendeds[x][y - 1].getActivated()))
                        && (!(buttonExtendeds[x + 1][y].getActivated() &&
                        buttonExtendeds[x - 1][y].getActivated()))) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            }
        } else if (x == 0) {//левый край
            if (y > 0 && y < 9) {
                if ((!buttonExtendeds[x + 1][y + 1].getActivated()) &&
                        (!buttonExtendeds[x + 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            } else if (y == 0) {
                if ((!buttonExtendeds[x + 1][y + 1].getActivated())) {
                    if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            } else if (y == 9) {
                if ((!buttonExtendeds[x + 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            }
        } else if (x == 9) {
            if (y > 0 && y < 9) {
                if ((!buttonExtendeds[x - 1][y + 1].getActivated()) &&
                        (!buttonExtendeds[x - 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            } else if (y == 0) {
                if ((!buttonExtendeds[x - 1][y + 1].getActivated())) {
                    if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            } else if (y == 9) {
                if ((!buttonExtendeds[x - 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        ships.add(new Ship(buttonExtended));
                    }
                }
            }
        } else if (y == 0){
            if ((!buttonExtendeds[x + 1][y + 1].getActivated()) &&
                    (!buttonExtendeds[x - 1][y + 1].getActivated())) {
                if (buttonExtendeds[x-1][y].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x-1][y]))
                            ship.extendShip(buttonExtended);
                    });
                } else if (buttonExtendeds[x+1][y].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x+1][y]))
                            ship.extendShip(buttonExtended);
                    });
                } else if (buttonExtendeds[x][y+1].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y+1]))
                            ship.extendShip(buttonExtended);
                    });
                } else {
                    ships.add(new Ship(buttonExtended));
                }
            }
        } else if (y == 9){
            if ((!buttonExtendeds[x + 1][y - 1].getActivated()) &&
                    (!buttonExtendeds[x - 1][y - 1].getActivated())) {
                if (buttonExtendeds[x-1][y].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x-1][y]))
                            ship.extendShip(buttonExtended);
                    });
                } else if (buttonExtendeds[x+1][y].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x+1][y]))
                            ship.extendShip(buttonExtended);
                    });
                } else if (buttonExtendeds[x][y-1].getActivated()) {
                    ships.forEach(ship -> {
                        if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y-1]))
                            ship.extendShip(buttonExtended);
                    });
                } else {
                    ships.add(new Ship(buttonExtended));
                }
            }
        }
    }
}


