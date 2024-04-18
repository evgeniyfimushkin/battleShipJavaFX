package edu.evgen.client;

import edu.evgen.controllers.MainController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Slf4j
public class ClientController {
    private List<String> clients;
    MainController mainController;

    public ClientController() {
        clients = new ArrayList<>();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void refreshClients(Message message) {
        clients.clear();
        clients.addAll((Collection<? extends String>) message.getList());
    }
}
