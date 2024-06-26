package edu.evgen.game;

import edu.evgen.Session;
import edu.evgen.client.Message;
import edu.evgen.client.MessageMarker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

import static edu.evgen.client.MessageMarker.*;

@Getter
@Setter
@Slf4j
public class Game {

    Player player1, player2;
    Session player1Sesiion, player2Sesiion;

    public Game(Player player1, Player player2, Session player1Sesiion, Session player2Sesiion) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Sesiion = player1Sesiion;
        this.player2Sesiion = player2Sesiion;

        log.info("New Game \n {} \n {}", this.player1, this.player2);
    }

    public void readySecond(Session session) {
        log.info("readySecond");
        if (player2Sesiion.equals(session)) {
            player1.setAction(MOVE);
            player2.setAction(WAIT);
            move();
        }
    }

    public Boolean isMyGame(String id) {
        return player1.toString().equals(id) || player2.toString().equals(id);
    }

    private void move() {
        Message player1Message = new Message(player1.getAction(), player1.getId(), player1.getId(), null);
        player1Sesiion.sendMarkerMessage(player1Message);
        Message player2Message = new Message(player2.getAction(), player2.getId(), player2.getId(), null);
        player2Sesiion.sendMarkerMessage(player2Message);
    }


    public void moveChange(String recipient) {
        if (player1.toString().equals(recipient)) {
            player1.setAction(WAIT);
            player2.setAction(MOVE);
            move();
        }else if (player2.toString().equals(recipient)){
            player1.setAction(MOVE);
            player2.setAction(WAIT);
            move();
        }
    }

    public void restartRequest(String recipient) {
        if (player1.toString().equals(recipient)) {
            player1.setAction(RESTART);
        }else if (player2.toString().equals(recipient)){
            player2.setAction(RESTART);
        }
        if (player1.getAction().equals(RESTART) && player2.getAction().equals(RESTART)){
            Message message1 = new Message(RESTART, player2.getId(), player1.getId(),null);
            Message message2 = new Message(RESTART, player1.getId(), player2.getId(),null);
            player1Sesiion.sendMarkerMessage(message1);
            player2Sesiion.sendMarkerMessage(message2);
        }
    }
}
