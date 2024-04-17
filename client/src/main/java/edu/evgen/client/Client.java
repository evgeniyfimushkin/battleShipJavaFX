package edu.evgen.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.evgen.client.DTO.ClientDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Slf4j
public class Client {
    ClientDTO clientDTO = new ClientDTO();

    @SneakyThrows
    public Boolean connect(String address, Integer port){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientDTO);
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":"+ port + "/api/connect";

        ResponseEntity<ClientDTO> response = restClient.post()
                .uri(urlServer)
                .contentType(APPLICATION_JSON)
                .body(clientDTO)
                .retrieve()
                .toEntity(ClientDTO.class);
        clientDTO = response.getBody();
        log.info(clientDTO.toString());
        getClients(address, port);
        return true;
    }
    @SneakyThrows
    public Boolean getClients(String address, Integer port){
        ObjectMapper objectMapper = new ObjectMapper();
        RestClient restClient = RestClient.create();
        String urlServer = "http://" + address + ":"+ port + "/api/connect";

        List request = restClient.get()
                .uri(urlServer)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(List.class);

        ClientsRepository.clients = request;
        log.info(ClientsRepository.clients.toString());
        return true;
    }
}
