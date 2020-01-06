package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.FileDTO;

import java.util.List;

@Service
public interface BibliographyService {
    void createBibliography(Bibliography bibliography);

    void addFileToBibliography(Bibliography bibliography, FileDTO file);

    void deleteFileFromBibliography(Bibliography bibliography, FileDTO file);

    List<Bibliography> listBibliographies();

    void deleteBibliography(Bibliography bibliography);
}
