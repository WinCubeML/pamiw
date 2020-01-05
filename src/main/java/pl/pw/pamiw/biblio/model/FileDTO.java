package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("files")
@Data
@NoArgsConstructor
public class FileDTO {

    @Id
    private String fileName;

    private String authorName;

    private String authorSurname;

}
