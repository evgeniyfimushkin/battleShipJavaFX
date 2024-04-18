package edu.evgen.client;

public enum MessageMarker {
    SETID("@SETID@"),
    SESSIONS("@SESSIONS@"),
    DISCONNECT("@DISCONNECT@"),
    OFFERREQUEST("@OFFERREQUEST@"),
    OFFERRESPONSE("OFFERRESPONSE"),
    STARTGAMING("@STARTGAMING@"),
    FINISHHAMING("@FINISHHAMING@"),
    MOVE("@MOVE@"),
    WAIT("@WAIT@"),
    SHOTREQUEST("@SHOTREQUEST@"),
    SHOTRESPONSE("@SHOTRESPONSE@"),
    WIN("@WIN@"),
    ENDGAME("@ENDGAME@"),
    READY("@READY@");

    private final String marker;

    MessageMarker(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}
