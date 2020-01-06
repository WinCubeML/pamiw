package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("bibliography")
@Data
@NoArgsConstructor
public class Bibliography { //TODO bibliografia
    @Id
    private String TODO;
}
