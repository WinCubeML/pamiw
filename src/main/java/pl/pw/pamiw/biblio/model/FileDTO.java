package pl.pw.pamiw.biblio.model;

import org.springframework.data.annotation.Id;

public class FileDTO { //TODO dokończyć fileDTO
    @Id
    private String id;
    private String fileName;
    private String authorName;
    private String authorSurname;
    private byte[] file;
}
