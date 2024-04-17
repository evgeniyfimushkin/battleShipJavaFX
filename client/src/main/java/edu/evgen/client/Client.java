package edu.evgen.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.evgen.client.DTO.ClientDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static edu.evgen.client.ClientStatus.NONE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Client {
    String address;
    Integer port;
    ClientDTO clientDTO = new ClientDTO();
    ClientStatus clientStatus = NONE;
    Thread threadGetStatus;

    public Client(String address, Integer port) {
        this.address = address;
        this.port = port;
        connect();
        startPlaying(new ClientDTO(1));
        threadGetStatus = new Thread(() -> {
            while (true)
                checkStatus();
        });
        threadGetStatus.start();
    }

    @SneakyThrows
    private void checkStatus() {
        Thread.sleep(1000L);
        clientStatus = getStatus();
        log.info(clientStatus.toString());
    }

    @SneakyThrows
    public ClientStatus getStatus() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientDTO);
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":" + port + "/api/status";

        ResponseEntity<ClientStatus> request = restClient.post()
                .uri(urlServer)
                .contentType(APPLICATION_JSON)
                .body(clientDTO)
                .retrieve()
                .toEntity(ClientStatus.class);

        return request.getBody();
    }

    @SneakyThrows
    public Boolean connect() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientDTO);
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":" + port + "/api/connect";

        ResponseEntity<ClientDTO> response = restClient.post()
                .uri(urlServer)
                .contentType(APPLICATION_JSON)
                .body(clientDTO)
                .retrieve()
                .toEntity(ClientDTO.class);
        clientDTO = response.getBody();
        log.info(clientDTO.toString());
        getClients();
        return true;
    }

    @SneakyThrows
    public Boolean getClients() {
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":" + port + "/api/connect";

        List request = restClient.get()
                .uri(urlServer)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(List.class);

        ClientsRepository.clients = request;
        log.info(ClientsRepository.clients.toString());
        return true;
    }

    @SneakyThrows
    public Boolean startPlaying(ClientDTO clientDTO2) {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientDTOList.add(clientDTO);
        clientDTOList.add(clientDTO2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientDTOList);
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":" + port + "/api/play";

        ResponseEntity<String> response = restClient.post()
                .uri(urlServer)
                .contentType(APPLICATION_JSON)
                .body(json)
                .retrieve()
                .toEntity(String.class);
        log.info(response.getBody());
        return true;
    }


}