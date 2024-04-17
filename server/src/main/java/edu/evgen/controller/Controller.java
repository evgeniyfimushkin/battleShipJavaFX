package edu.evgen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.evgen.DTO.ClientDTO;
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

    @PostMapping("/api/connect")
    @SneakyThrows
    private List<ClientDTO> connectClient() {
        log.info("New Client: " + clientRepository.save(new ClientDTO()));
        return clientRepository.findAll();
    }
    @DeleteMapping("/api/connect")
    @SneakyThrows
    private void disconnectClient(@RequestParam Integer id){
        clientRepository.deleteById(id);
    }
}
