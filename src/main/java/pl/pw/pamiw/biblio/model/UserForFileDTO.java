package pl.pw.pamiw.biblio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForFileDTO {
    private String name;
    private String surname;
}
