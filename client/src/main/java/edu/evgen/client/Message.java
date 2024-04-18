package edu.evgen.client;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Message implements Serializable {
    private List<?> list;
    private MessageMarker marker;
    private String sender;
    private String recipient;

    public Message(MessageMarker marker, String sender, String recipient, List<?> list) {
        this.marker = marker;
        this.sender = sender;
        this.recipient = recipient;
        this.list = list;
    }
}
