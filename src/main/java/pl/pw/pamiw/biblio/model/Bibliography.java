package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RedisHash("bibliography")
@Data
@NoArgsConstructor
public class Bibliography {
    @Id
    private long bibliographyId;

    private String publicationTitle;

    private String publicationAuthor;

    @Min(0)
    private int publicationPageCount;

    @Min(-10000)
    @Max(3000)
    private int publicationYear;

    private List<String> files;
}
