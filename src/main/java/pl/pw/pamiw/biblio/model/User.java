package pl.pw.pamiw.biblio.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String login;

    private String password;

    private String name;

    private String surname;

    private String pesel;

}
