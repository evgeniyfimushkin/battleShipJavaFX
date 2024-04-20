package edu.evgen;

import edu.evgen.client.Message;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static edu.evgen.client.MessageMarker.SETID;

@Slf4j
@Data
public class Session implements Closeable {
    private final Server server;
    public final Socket socket;
    public String id;
    public String opponent;
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
            new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
        } else {
            server.sessions
                    .stream()
                    .filter(s -> s.id.equals(message.getRecipient()))
                    .forEach(s -> s.transport(message));
        }
    }

    @SneakyThrows
    private void disconnect() {
        this.close();
        run = false;
        listener.interrupt();
    }

    @Override
    @SneakyThrows
    public void close() {
        server.ids.remove(this.id);
        server.sessions.remove(this);
        server.sessions.forEach(server::sendSessions);
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
    public void sendMarkerMessage(Message message){
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        List<String> list = new ArrayList<>();
        list.add(getId());
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    @SneakyThrows
    public void listen() {
        try {
            while (true) {

                Message message = (Message) new ObjectInputStream(socket.getInputStream()).readObject();

                if (message == null) {
                    continue;
                }

                log.info("{}: {}", message.getMarker(), message);

                switch (message.getMarker()) {
                    case SESSIONS:
                        server.sendSessions(this);
                        break;
                    case READY:
                        transport(message);
                        server.newGame(this);
                        break;
                    case SHOTRESPONSE:
                        transport(message);
                        server.games
                                .stream()
                                .filter((game -> game.isMyGame(id)))
                                .forEach(game -> game.moveChange(message.getRecipient()));
                        break;
                    case STARTGAMING:
                        opponent = message.getSender();
                        transport(message);
                        break;
                    case WIN:
                    case ENDGAME:
                    case SHOTREQUEST:
                    case OFFERREQUEST:
                    case OFFERRESPONSE:
                        transport(message);
                        break;
                    case DISCONNECT:
                        disconnect();
                        break;
                    case RESTART:
                        server.games.stream()
                                .filter((game -> game.isMyGame(id)))
                                .forEach(game -> game.restartRequest(message.getRecipient()));
                        break;
                    default:
                        break;
                }
            }
        } catch (SocketException e){
            close();
        }
    }

}
