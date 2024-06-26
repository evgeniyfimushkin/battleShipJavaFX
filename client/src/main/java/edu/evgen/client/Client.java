package edu.evgen.client;

import edu.evgen.game.ship.ButtonExtended;
import javafx.scene.control.Alert;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static edu.evgen.client.MessageMarker.*;

@RequiredArgsConstructor
@Slf4j
@Getter
@Setter
public class Client implements Closeable {

    private final Socket socket;
    private MessageMarker status = EMPTY;
    public String id;
    private String opponent;
    private Thread listener = new Thread(this::listen);
    private ClientController clientController;
    private String address;


    public Client(String address, Integer port, ClientController clientController) throws IOException {
        Socket socket1;
        this.clientController = clientController;
        try {
            socket1 = new Socket(address, port);
//            info(String.format("Connection Succedd %s:%d", address, port));
        } catch (Throwable e) {
            socket1 = null;
            info(String.format("Failed connection %s:%d", address, port));
            throw e;
        }
        this.socket = socket1;
        listener.start();
    }


    @Synchronized
    @SneakyThrows
    public void listen() {
        while (true) {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objectInputStream.readObject();

            log.info("MESSAGE {}", message.getMarker());

            if (message == null) {
                continue;
            }

            switch (message.getMarker()) {
                case SESSIONS:
                    clientController.refreshClients(message);
                    break;
                case SETID:
                    getIdFromServer(message);
                    break;
                case READY:
                    status = message.getMarker();
                    clientController.oponentIsReady(message);
                    break;
                case MOVE:
                    status = message.getMarker();
                    clientController.moveCommand();
                    break;
                case WAIT:
                    status = message.getMarker();
                    clientController.waitCommand();
                    break;
                case SHOTREQUEST:
                        clientController.shotRequestHandler(message);
                    break;
                case SHOTRESPONSE:
                        clientController.shotResponseHandler(message);
                    break;
                case OFFERREQUEST:
                    if (status.equals(LOBBY))
                    clientController.askNewGame(message);
                    break;
                case OFFERRESPONSE:
                    if (status.equals(LOBBY))
                    clientController.offerResponseRead(message);
                    break;
                case ENDGAME:
                    status = message.getMarker();
                    clientController.winHandler(message);
                    break;
                case RESTART:
                    clientController.restartHandler(message);
                    break;
                case STARTGAMING:
                    status = message.getMarker();
                    break;
                default:
                    break;
            }
        }
    }

    @SneakyThrows
    private void getIdFromServer(Message message) {
        id = (String) message.getList().getLast();
        log.info("getIdFromServer -> {}", id);
        clientController.printMyId(id);
//        controller.printId(id);
    }

    @Override
    @SneakyThrows
    public void close() {
//        controller.clientsTextArea.setText("Connection Lost");
//        controller.clientIdLabel.setText("id: null");
//        controller.networkStatusLabel.setText("Status: Offline");
        log.info("DISCONNECT");
        listener.interrupt();
        Message message = new Message(DISCONNECT, id, id, null);
        ObjectOutputStream outputStream;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);
        socket.close();
    }

    private void info(String string) {
        log.info(string);
        //Вывод в окошко
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    @SneakyThrows
    public void getSessionsRequest() {
        Message message = new Message(SESSIONS, id, id, null);

        log.info("getSessionsRequest {}", message);
        ObjectOutputStream outputStream;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);
    }

    @SneakyThrows
    public void sendEmptyMessage(MessageMarker messageMarker, String idResiept) {
        if (clientController.getClients().contains(idResiept)
//                && !id.equals(idResiept)
        ) {
            Message message = new Message(messageMarker, id, idResiept, null);
            log.info("{}", message.getMarker());
            ObjectOutputStream outputStream;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
        }
    }

    @SneakyThrows
    public void sendNotEmptyMessage(Message message) {
        log.info("{}", message.getMarker());
        ObjectOutputStream outputStream;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);

    }

    public void ready() {
        sendEmptyMessage(READY, opponent);
    }

    public void sendShootRequest(ButtonExtended buttonExtended) {
        List<Integer> list = new ArrayList<>();
        list.add(buttonExtended.getX());
        list.add(buttonExtended.getY());
        Message message = new Message(SHOTREQUEST, id, opponent, list);
        sendNotEmptyMessage(message);
    }

    public void sendShootResponse(Message message, Integer isHitted) {
        List<Integer> list = new ArrayList<>();
        list.add((Integer) message.getList().getFirst());
        list.add((Integer) message.getList().getLast());
        list.add(isHitted);
        sendNotEmptyMessage(new Message(SHOTRESPONSE, id, message.getSender(), list));
    }


    public void sendEndGameMessage() {
        status = ENDGAME;
        sendEmptyMessage(ENDGAME, opponent);
    }

    public void restartRequest() {
        sendEmptyMessage(RESTART, opponent);
    }
}
