package edu.evgen;

import edu.evgen.client.Message;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static edu.evgen.client.MessageMarker.*;

@Slf4j
@Data
public class Session implements Closeable {
    private final Server server;
    public final Socket socket;
    public String id;
    Boolean run;
    Thread listener = new Thread(this::listen);

    public Session(Server server, Socket socket, String id) {
        this.server = server;
        this.socket = socket;
        this.id = id;
        listener.start();
    }

    @SneakyThrows
    @Synchronized
    private void transport(Message message) {
        log.info("Transport with marker {}", message.getMarker());
        if (id.equals(message.getRecipient())) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        } else {
            for (Session iter : server.sessions) {
                if (message.getRecipient().equals(iter.id))
                    iter.transport(message);
            }
        }
    }

    @SneakyThrows
    private void disconnect() {
        server.ids.remove(id);
        server.sessions.remove(this);
        server.sessions.forEach(server::sendSessions);
        run = false;
        listener.interrupt();
        socket.close();
    }

    @SneakyThrows
    public void sendId() {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        List<String> list = new ArrayList<>();
        list.add(getId());
        Message message = new Message(SETID, id, id, list);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    @SneakyThrows
    public void listen() {
        try {
            while (true) {

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();

                if (message == null) {
                    continue;
                }

                switch (message.getMarker()) {
                    case SESSIONS:
                        log.info("getSessionsRequest");
                        server.sendSessions(this);
                        break;
                    case SHOTREQUEST:
                        transport(message);
                        break;
                    case SHOTRESPONSE:
                        transport(message);
                        //MOVE,WIN
                        break;
                    case WIN:
                        transport(message);
                        break;
                    case ENDGAME:
                        transport(message);
                        break;
                    case STARTGAMING:
                        transport(message);
                        break;
                    case OFFERREQUEST:
                        transport(message);
                        break;
                    case OFFERRESPONSE:
                        transport(message);
                        break;
                    case DISCONNECT:
                        disconnect();
                        break;
                    default:
                        break;
                }
            }
        } catch (SocketException e){
            close();
        }
    }

    @Override
    @SneakyThrows
    public void close() throws IOException {
        server.ids.remove(this.id);
        server.sessions.remove(this);
        server.sessions.forEach(server::sendSessions);
        socket.close();
    }
}
