package edu.evgen;

import edu.evgen.client.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static edu.evgen.client.MessageMarker.*;

@RequiredArgsConstructor
@Slf4j
public class Server implements Runnable {
    public final List<Session> sessions = new LinkedList<>();
    public final Set<String> ids = new HashSet<>();
    private Integer port;

    public Server(Integer port) {
        this.port = port;
    }

    @Override
    @SneakyThrows
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                log.info("Server is waiting connetion on port {}", port);
                Socket socket = serverSocket.accept();
                sessions.add(new Session(this, socket, getId()));
                sessions.forEach(this::sendSessions);
                sessions.getLast().sendId();
                log.info("Client connected. Session {}. Clients count {}", sessions.getLast().getId(), sessions.size());
            }
        }
    }
    @SneakyThrows
    public void sendSessions(Session session){
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(session.socket.getOutputStream());
        Message message = new Message(SESSIONS, session.id, session.id, new ArrayList<>(ids));
        objectOutputStream.writeObject(message);
    }
    private String getId() {
        String currentId = UUID.randomUUID().toString();
        if (!ids.contains(currentId)) {
            ids.add(currentId);
            return currentId;
        } else return getId();
    }
}
