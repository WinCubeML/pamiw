package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.FileDTO;

import java.util.List;

@Service
public interface FileService { //TODO interfejs serwisu plików
    FileDTO uploadFile(FileDTO file);

    List<FileDTO> listAllFiles();

    byte[] downloadFile(FileDTO file);

    void deleteFile(FileDTO file); // TODO czy na pewno przy delete będzie fileDTO?
}
