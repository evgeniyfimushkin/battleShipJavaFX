package edu.evgen.client.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    Integer id;
}
