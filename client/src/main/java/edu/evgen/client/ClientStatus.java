package edu.evgen.client;

import lombok.ToString;

@ToString
public enum ClientStatus {
    NONE("NONE"),
    CONNECTED("CONNECTED"),
    GAMING("GAMING"),
    SHOT("SHOT"),
    WIN("WIN"),
    LOSE("LOSE");
    private String status;

    ClientStatus(String status) {
        this.status = status;
    }
}
