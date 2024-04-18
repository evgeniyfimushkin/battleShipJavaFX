package edu.evgen.client;

import edu.evgen.controllers.AbstractController;
import edu.evgen.controllers.MainController;
import edu.evgen.controllers.StartController;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Slf4j

public class ClientController {
    private List<String> clients = new ArrayList<>();
    AbstractController controller;

    public ClientController() {
    }

    public ClientController(AbstractController controller) {
        this.controller = controller;
    }

    public void refreshClients(Message message) {
        if (clients != null)
            clients.clear();
        ((StartController) controller).getClientListTextArea().clear();
        clients.addAll((Collection<? extends String>) message.getList());
        message.getList().forEach(id -> ((StartController) controller).getClientListTextArea().appendText((String) id + "\n"));
//        ((StartController) controller).getClientListTextArea().setText(message.getList().toString());
//        startController.getClientIdTextField().setText(clients.toString());

    }
}
