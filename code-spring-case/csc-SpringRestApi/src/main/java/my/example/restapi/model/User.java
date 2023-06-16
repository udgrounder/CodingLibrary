package my.example.restapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString(callSuper = true)
public class User {

    Optional<String> name;
    Optional<Integer> age;
    Optional<String> nickname;

    Optional<Address> address;


}
