package pl.pw.pamiw.biblio.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("sessiondata")
@Getter
@Setter
@NoArgsConstructor
public class SessionData {

    @Id
    private String sessionId;

    private String login;

    private String expiryDate;

}
