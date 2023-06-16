package my.example.restapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class Address {

    Optional<String> oldAddress;
    Optional<String> newAddress;

}
