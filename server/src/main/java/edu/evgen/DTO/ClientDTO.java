package edu.evgen.DTO;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Entity
@Table(name = "BattleShipClients")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ClientDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    public ClientDTO() {
    }
}
