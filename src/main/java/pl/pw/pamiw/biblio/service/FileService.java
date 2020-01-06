package pl.pw.pamiw.biblio.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pw.pamiw.biblio.model.FileDTO;
import pl.pw.pamiw.biblio.model.UserForFileDTO;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    void uploadFile(UserForFileDTO user, @NonNull MultipartFile file) throws IOException;

    List<FileDTO> listAllFiles();

    boolean isFileNameTaken(String fileName);

    byte[] downloadFile(String fileName) throws IllegalAccessException, IOException;

    void deleteFile(String fileName);
}
