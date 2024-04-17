package edu.evgen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.evgen.DTO.ClientDTO;
import edu.evgen.client.ClientStatus;
import edu.evgen.repository.ClientRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Controller {

    final ClientRepository clientRepository;

    @GetMapping("/api/connect")
    @SneakyThrows
    private List<ClientDTO> getClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/api/connect/client")
    @SneakyThrows
    private String getClients(@RequestBody ClientDTO clientDTO) {
        if (clientRepository.findById(clientDTO.getId()).isPresent()) return "CONNECTED";
        else return "DISCONNECTED";
    }

    @PostMapping("/api/connect")
    @SneakyThrows
    private ClientDTO connectClient() {
        ClientDTO clientDTO = new ClientDTO();
        log.info("New Client: " + clientRepository.save(clientDTO));
        return clientDTO;
    }

    @DeleteMapping("/api/connect")
    @SneakyThrows
    private void disconnectClient(@RequestBody ClientDTO clientDTO) {
        clientRepository.delete(clientDTO);
    }
    @PostMapping("/api/play")
    @SneakyThrows
    private String startPlaying(@RequestBody List<edu.evgen.client.DTO.ClientDTO> clientDTOList){
        if (clientRepository.findById(clientDTOList.get(0).getId()).isPresent() && clientRepository.findById(clientDTOList.get(1).getId()).isPresent()){

            return "GAMINGA "+ clientDTOList;
        }
        else return "NO GAMINGA";
    }

    @PostMapping("/api/status")
    @SneakyThrows
    private ClientStatus getStatus(@RequestBody edu.evgen.client.DTO.ClientDTO clientDTO){
        return ClientStatus.NONE;
    }
}
