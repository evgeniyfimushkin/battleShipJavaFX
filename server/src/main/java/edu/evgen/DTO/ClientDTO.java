package edu.evgen.DTO;

import edu.evgen.client.ClientStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Entity
@Table(name = "BattleShipClient")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ClientDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Transient
    ClientStatus clientStatus;

    public ClientDTO() {
        clientStatus = ClientStatus.NONE;
    }
}
