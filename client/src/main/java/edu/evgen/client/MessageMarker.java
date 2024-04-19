package edu.evgen.client;

public enum MessageMarker {
    SETID("@SETID@"),
    SESSIONS("@SESSIONS@"),
    DISCONNECT("@DISCONNECT@"),
    OFFERREQUEST("@OFFERREQUEST@"),
    OFFERRESPONSE("OFFERRESPONSE"),
    STARTGAMING("@STARTGAMING@"),
    RESTART("@RESTART@"),
    MOVE("@MOVE@"),
    WAIT("@WAIT@"),
    SHOTREQUEST("@SHOTREQUEST@"),
    SHOTRESPONSE("@SHOTRESPONSE@"),
    WIN("@WIN@"),
    ENDGAME("@ENDGAME@"),
    READY("@READY@"),
    EMPTY("@EMPTY@");//for not NPE

    private final String marker;

    MessageMarker(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}
