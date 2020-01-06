package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pw.pamiw.biblio.model.FileDTO;
import pl.pw.pamiw.biblio.model.UserForFileDTO;
import pl.pw.pamiw.biblio.repositories.FileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService { //TODO implementacja serwisu plików

    private FileRepository fileRepository;

    private final String FILE_DIR = "FILES";
    private final String FILE_TEMP = "TMP";

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void uploadFile(UserForFileDTO user, @NonNull MultipartFile file) throws IOException {
        String path = Paths.get(FILE_DIR).toAbsolutePath().toString();
        Path dirPath = Paths.get(path);
        if (!dirPath.toFile().exists()) {
            dirPath.toFile().mkdir();
        }
        Path filePath = Paths.get(dirPath.toString(), file.getOriginalFilename());
        file.transferTo(filePath.toFile());

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(file.getOriginalFilename());
        fileDTO.setAuthorName(user.getName());
        fileDTO.setAuthorSurname(user.getSurname());
        fileRepository.save(fileDTO);
    }

    @Override
    public List<FileDTO> listAllFiles() {
        File dir = Paths.get(FILE_DIR).toFile();
        if (!dir.exists() || null == dir.listFiles())
            return new ArrayList<>();

        List<String> fileNames = Arrays.stream(dir.listFiles()).map(file -> file.getName()).collect(Collectors.toList());
        List<FileDTO> result = new ArrayList<>();
        for (String fileName : fileNames) {
            fileRepository.findById(fileName).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public boolean isFileNameTaken(String fileName) {
        return fileRepository.findById(fileName).isPresent();
    }

    @Override
    public byte[] downloadFile(String fileName) throws IllegalAccessException, IOException {
        String path = Paths.get(FILE_DIR).toAbsolutePath().toString();
        Path filePath = Paths.get(path, fileName);
        File file = new File(String.valueOf(filePath));
        if (!file.exists()) {
            throw new IllegalAccessException("Brak dostępu do pliku");
        }
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public void deleteFile(String fileName) {

    }
}
