package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("bibliography")
@Data
@NoArgsConstructor
public class Bibliography {
    @Id
    private long bibliographyId;

    private String publicationTitle;

    private String publicationAuthor;

    private int publicationPageCount = 0;

    private int publicationYear = 2020;

    private List<String> files;
}
