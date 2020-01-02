package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.FileDTO;

import java.util.List;

@Service
public class FileServiceImpl implements FileService { //TODO implementacja serwisu plików
    @Override
    public FileDTO uploadFile(FileDTO file) {
        return null;
    }

    @Override
    public List<FileDTO> listFiles() {
        return null;
    }

    @Override
    public FileDTO downloadFile(FileDTO file) {
        return null;
    }

    @Override
    public void deleteFile(FileDTO file) {

    }
}
