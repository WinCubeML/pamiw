package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.FileDTO;

@Service
public interface BibliographyService {
    void createBibliography(Bibliography bibliography);

    void editBibliography(Bibliography oldB, Bibliography newB);

    void addFileToBibliography(Bibliography bibliography, FileDTO file);

    void deleteFileFromBibliography(Bibliography bibliography, FileDTO file);

    void listBibliographies();

    void deleteBibliography(Bibliography bibliography);
}
