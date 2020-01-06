package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;

import java.util.List;

@Service
public interface BibliographyService {
    void createBibliography(Bibliography bibliography);

    void addFileToBibliography(String bibliographyId, String fileName);

    Bibliography getBibliographyFromId(String bibliographyId);

    void deleteFileFromBibliography(String bibliographyId, String fileName);

    List<Bibliography> listBibliographies();

    void deleteBibliography(Bibliography bibliography);
}
