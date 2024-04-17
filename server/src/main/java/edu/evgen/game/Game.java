package edu.evgen.game;

import edu.evgen.client.DTO.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private ClientDTO clientDTO1;
    private ClientDTO clientDTO2;
}