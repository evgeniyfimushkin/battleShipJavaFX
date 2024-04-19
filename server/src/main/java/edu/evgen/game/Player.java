package edu.evgen.game;

import edu.evgen.client.MessageMarker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    public String id;
    MessageMarker action;

    public Player(String id) {
        this.id = id;
    }

    public String toString() {
        return  this.getId();
    }
}
