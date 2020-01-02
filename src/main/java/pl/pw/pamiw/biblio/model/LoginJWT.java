package pl.pw.pamiw.biblio.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("jwt")
@Getter
@Setter
@NoArgsConstructor
public class LoginJWT {
    @Id
    private Long id;
    private String login;
    private String JWT;
}
