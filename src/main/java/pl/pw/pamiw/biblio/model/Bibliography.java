package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("bibliography")
@Data
@NoArgsConstructor
public class Bibliography { //TODO bibliografia
    @Id
    private long bibliographyId;

    private String publicationName;

    private String publicationAuthor;

    private int publicationPageCount;

    private int publicationYear;

    private List<String> files;
}
