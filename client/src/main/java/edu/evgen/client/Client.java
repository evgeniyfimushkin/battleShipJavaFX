package edu.evgen.client;

import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

import static edu.evgen.client.MessageMarker.*;

@RequiredArgsConstructor
@Slf4j
public class Client implements Closeable {

    private final Socket socket;
    public String id;
    private Thread listener = new Thread(this::listen);
    private ClientController clientController;
    private String address;


    public Client(String address, Integer port, ClientController clientController) {
        Socket socket1;
        this.clientController = clientController;
        try {
            socket1 = new Socket(address, port);
            info(String.format("Connection Succedd %s:%d", address, port));
        }catch (Throwable e){
            socket1 = null;
            info(String.format("Failed connection %s:%d", address, port));
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
                case MOVE:

                    break;
                case WAIT:

                    break;
                case SHOTREQUEST:

                    break;
                case SHOTRESPONSE:

                    break;
                case WIN:

                    break;
                case OFFERREQUEST:

                    break;
                case OFFERRESPONSE:

                    break;
                case ENDGAME:

                    break;
                case STARTGAMING:

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
    private void info(String string){
        log.info(string);
        //Вывод в окошко
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }
}
