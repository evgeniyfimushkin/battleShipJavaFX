package edu.evgen.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
public class Message implements Serializable {
    private MessageMarker marker;
    private String sender;
    private String recipient;
    private List<?> list;
}
