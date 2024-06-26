package edu.evgen.game.fields;

import edu.evgen.controllers.MainController;
import edu.evgen.game.ship.ButtonExtended;
import edu.evgen.game.ship.Ship;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static edu.evgen.client.MessageMarker.*;

@Data
public class MyField extends Field {
    @Getter
    private ArrayList<Ship> ships = new ArrayList<>();
    private MainController controller;

    public MyField(MainController controller) {
        this.controller = controller;
        Ship.myField = this;
        ButtonExtended.myField = this;
    }


    public void buttonCreateShipsAction(ButtonExtended buttonExtended) {
        if (buttonExtended.getActivated()) {
            cutShip(buttonExtended);
        } else {
            createShip(buttonExtended);
            Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
        }
        System.out.println(buttonExtended.getFieldType() + " " + buttonExtended.getX() + " " + buttonExtended.getY());
    }

    //логика сокращения, удаления ships
    private void cutShip(ButtonExtended buttonExtended) {
        ArrayList<Ship> shipsToDelete = new ArrayList<>();
        Comparator<ButtonExtended> comparator = Comparator.comparing(ButtonExtended::getX)
                .thenComparing(ButtonExtended::getY);
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter(ship -> ship.getSize() == 1)
                .forEach(ship -> {
                    shipsToDelete.add(ship);
                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
                    buttonExtended.setActivated(false);
                    buttonExtended.getButton().setStyle("-fx-background-radius: 0;");
                });
        ships.stream()
                .filter(ship -> ship.getButtonExtendeds().contains(buttonExtended))
                .filter(ship -> ship.getSize() == 2)
                .forEach(ship -> {
                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
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

                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
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

                    Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
                    buttonExtended1.setActivated(false);
                    buttonExtended1.getButton().setStyle(" -fx-background-radius: 0;");
                    ship.setSize(ship.getSize() - 1);
                    ship.getButtonExtendeds().remove(buttonExtended1);
                });
        shipsToDelete
                .forEach(ship -> ships.remove(ship));
    }

    private void createShip(ButtonExtended buttonExtended) {
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
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                    extendShip(buttonExtended, ship);
                            });
                        } else if (buttonExtendeds[x][y + 1].getActivated()) {
                            ships.forEach(ship -> {
                                if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                    extendShip(buttonExtended, ship);
                            });
                        } else if (buttonExtendeds[x + 1][y].getActivated()) {
                            ships.forEach(ship -> {
                                if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                    extendShip(buttonExtended, ship);
                            });
                        } else {
                            if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            } else if (y == 9) {
                if ((!buttonExtendeds[x + 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                    extendShip(buttonExtended, ship);
                            });
                        } else if (buttonExtendeds[x][y + 1].getActivated()) {
                            ships.forEach(ship -> {
                                if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                    extendShip(buttonExtended, ship);
                            });
                        } else if (buttonExtendeds[x - 1][y].getActivated()) {
                            ships.forEach(ship -> {
                                if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                    extendShip(buttonExtended, ship);
                            });
                        } else {
                            if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            } else if (y == 9) {
                if ((!buttonExtendeds[x - 1][y - 1].getActivated())) {
                    if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x - 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x - 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x][y + 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y + 1]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
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
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x + 1][y].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x + 1][y]))
                                extendShip(buttonExtended, ship);
                        });
                    } else if (buttonExtendeds[x][y - 1].getActivated()) {
                        ships.forEach(ship -> {
                            if (ship.getButtonExtendeds().contains(buttonExtendeds[x][y - 1]))
                                extendShip(buttonExtended, ship);
                        });
                    } else {
                        if (!(this.getShips().stream().filter(ship -> ship.getSize() == 1).count() >= 4)) {
                            ships.add(new Ship(buttonExtended));
                        }
                    }
                }
            }
        }
    }

    public void clear(ActionEvent event) {
        if (controller.getClient().getStatus().equals(STARTGAMING)) {
            clearButtons();
            ships.clear();
            Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
        }
    }

    private void clearButtons() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttonExtendeds[row][col].setActivated(false);
                buttonExtendeds[row][col].setHited(false);
                buttonExtendeds[row][col].getButton().setText("");
                buttonExtendeds[row][col].getButton().setDisable(false);
                buttonExtendeds[row][col].getButton().setStyle(" -fx-background-radius: 0;");

            }
        }
    }

    public Integer setHit(Integer x, Integer y) {
        if (//если координаты являются частью корабля
                ships.stream().filter(ship -> ship.getButtonExtendeds().contains(buttonExtendeds[x][y])).count() > 0
        ) {
            Platform.runLater(() -> {
                buttonExtendeds[x][y].getButton().setStyle(" -fx-background-radius: 0; -fx-background-color: red");
                buttonExtendeds[x][y].getButton().setText("X");
                buttonExtendeds[x][y].setHited(true);
                //Для корабля, содержащего, эту кнопку, вызвать метод getHit - который проверяет убит ли кобаль полностью

            });
            Ship hittedShip = ships.stream().filter(ship -> ship.getButtonExtendeds().contains(buttonExtendeds[x][y])).toList().getLast();
            buttonExtendeds[x][y].setHited(true);
            if (getHit(hittedShip))
                return 2;
            else
                return 1;
        } else {// если не являются частью корабля
            Platform.runLater(() -> {
                buttonExtendeds[x][y].getButton().setText("*");
                buttonExtendeds[x][y].setHited(true);
            });
            return 0;
        }

    }

    //Расширение корабля
    public void extendShip(ButtonExtended buttonExtended, Ship ship) {
        if (ship.getSize() > 0 && ship.getSize() < 4) {
            System.out.println("extend" + size + " " + this.getShips().stream().filter(ship1 -> ship1.getSize() == ship.getSize()).count());
            if ((ship.getSize() == 3) && (this.getShips().stream().filter(ship1 -> ship1.getSize() == 4).count() >= 1)) {
                System.out.println("ASDASDASD");
                return;
            } else if ((ship.getSize() == 2) && (this.getShips().stream().filter(ship1 -> ship1.getSize() == 3).count() >= 2)) {
                return;
            } else if ((ship.getSize() == 1) && (this.getShips().stream().filter(ship1 -> ship1.getSize() == 2).count() >= 3)) {
                return;
            }
            ship.setSize(ship.getSize() + 1);
            ship.getButtonExtendeds().add(buttonExtended);
            buttonExtended.setActivated(true);
            buttonExtended.getButton().setStyle("-fx-background-color: grey; -fx-background-radius: 0;");
            buttonExtended.getButton().setText("");
        }
    }

    //обработка попадания
    public Boolean getHit(Ship ship) {
        if (ship.getButtonExtendeds().stream().filter(buttonExtended -> (buttonExtended.getHited() == true)).count() == ship.getSize()) {
//            Platform.runLater(() -> {
            ship.getButtonExtendeds().forEach(buttonExtended -> buttonExtended.getButton().setDisable(true));
            this.killShip(ship);
//            });
            return true;
        }
        return false;
    }

    //обработка уничтожения корабля
    public void killShip(Ship ship) {
        ships.remove(ship);
        Platform.runLater(() -> controller.countShips.setText(ships.size() + "/10 ships"));
        if (ships.size() == 0) {
            controller.getClient().sendEndGameMessage();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You Lose! Want to play again?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                if (result == ButtonType.YES) {
                    controller.getClient().restartRequest();
                }else if (result == ButtonType.NO){
                    Platform.runLater(() -> {
                        controller.getReadyButton().setDisable(false);
                        controller.getReadyButton().setText("Back To Lobby");
                        controller.getReadyButton().setOnAction(controller::backToLobby);
                    });
                }
            });
        }
    }

    public void ready(ActionEvent event) {
        if (ships.size() == 10) {
            if (
                    (ships.stream().filter(ship -> ship.getSize() == 1).count() == 4)
                            && (ships.stream().filter(ship -> ship.getSize() == 2).count() == 3)
                            && (ships.stream().filter(ship -> ship.getSize() == 3).count() == 2)
                            && (ships.stream().filter(ship -> ship.getSize() == 4).count() == 1)
            ) {
                controller.getClient().ready();
                controller.getReadyButton().setDisable(true);
                controller.getInfo().setText("Waiting for opponent");
                setAllButtonsAction(empty -> {
                });
            } else {
                alert("Bad ships");
            }
        } else {
            alert("Ships less than 10");
        }

    }
}


