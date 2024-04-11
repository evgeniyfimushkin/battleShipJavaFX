package edu.evgen.game.fields;

import edu.evgen.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.Shot;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@Data
public class MyField extends Field {
    @Getter
    private static ArrayList<Ship> ships = new ArrayList<>();
    private static ArrayList<Shot> shots = new ArrayList<>();
    private static ButtonExtended[][] buttonExtendeds = new ButtonExtended[10][10];
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
            cutShip(buttonExtended);
        } else {
            createShip(buttonExtended);
            Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
        }
        System.out.println(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }

    //логика сокращения, удаления кораблей
    private static void cutShip(ButtonExtended buttonExtended) {
        ArrayList<Ship> shipsToDelete = new ArrayList<>();
        Comparator<ButtonExtended> comparator = Comparator.comparing(ButtonExtended::getX)
                .thenComparing(ButtonExtended::getY);
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter(ship -> ship.getSize() == 1)
                .forEach(ship -> {
                    shipsToDelete.add(ship);
                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
                    buttonExtended.setActivated(false);
                    buttonExtended.getButton().setStyle("-fx-background-radius: 0;");
                });
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter(ship -> ship.getSize() == 2)
                .forEach(ship -> {
                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
                    buttonExtended.setActivated(false);
                    buttonExtended.getButton().setStyle(" -fx-background-radius: 0;");
                    ship.setSize(ship.getSize() - 1);
                    ship.getButtonExtendeds().remove(buttonExtended);
                });
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter((ship -> ship.getSize() == 3))
                .forEach(ship -> {
                    Collections.sort(ship.getButtonExtendeds(), comparator);
                    ButtonExtended buttonExtended1 = ship.getButtonExtendeds().getLast();

                        Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
                        buttonExtended1.setActivated(false);
                        buttonExtended1.getButton().setStyle(" -fx-background-radius: 0;");
                        ship.setSize(ship.getSize() - 1);
                        ship.getButtonExtendeds().remove(buttonExtended1);
                });
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter((ship -> ship.getSize() == 4))
                .forEach(ship -> {
                    Collections.sort(ship.getButtonExtendeds(), comparator);
                    ButtonExtended buttonExtended1 = ship.getButtonExtendeds().getLast();

                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
                    buttonExtended1.setActivated(false);
                    buttonExtended1.getButton().setStyle(" -fx-background-radius: 0;");
                    ship.setSize(ship.getSize() - 1);
                    ship.getButtonExtendeds().remove(buttonExtended1);
                });
        shipsToDelete
                .forEach(ship -> ships.remove(ship));
    }
    //логика создания кораблей !! НЕ ПЫТАТЬСЯ РАЗОБРАТЬСЯ ИЛИ ЧТО-ТО ПОМЕНЯТЬ !!
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
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        } else if (x == 0) {//левый край
            if (y > 0 && y < 9) {
                if (!(buttonExtendeds[x][y - 1].getActivated() && buttonExtendeds[x][y + 1].getActivated())) {
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
                            if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                                ships.add(new Ship(buttonExtended));
                            }
                        }
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
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
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
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        } else if (x == 9) {
            if (y > 0 && y < 9) {
                if (!(buttonExtendeds[x][y - 1].getActivated() && buttonExtendeds[x][y + 1].getActivated())) {
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
                            if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                                ships.add(new Ship(buttonExtended));
                            }
                        }
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
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
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
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        } else if (y == 0) {
            if (!(buttonExtendeds[x + 1][y].getActivated() && buttonExtendeds[x - 1][y].getActivated())) {
                if ((!buttonExtendeds[x + 1][y + 1].getActivated()) &&
                        (!buttonExtendeds[x - 1][y + 1].getActivated())) {
                    if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        } else if (y == 9) {
            if (!(buttonExtendeds[x + 1][y].getActivated() && buttonExtendeds[x - 1][y].getActivated())) {
                if ((!buttonExtendeds[x + 1][y - 1].getActivated()) &&
                        (!buttonExtendeds[x - 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                ship.extendShip(buttonExtended);
                        });
                    } else if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                ship.extendShip(buttonExtended);
                        });
                    } else {
                        if (!(MyField.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        }
    }

    public static void clear(ActionEvent event) {
        ships.forEach(ship -> ship.getButtonExtendeds().forEach(buttonExtended -> buttonExtended.setActivated(false)));
        ships.clear();
        controller.getMyField().getChildren().forEach(button -> button.setStyle(" -fx-background-radius: 0;"));
        Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 кораблей"));
    }
}


