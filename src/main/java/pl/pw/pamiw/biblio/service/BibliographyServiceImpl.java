package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.FileDTO;
import pl.pw.pamiw.biblio.repositories.BibliographyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BibliographyServiceImpl implements BibliographyService {
    private BibliographyRepository bibliographyRepository;

    @Autowired
    public BibliographyServiceImpl(BibliographyRepository bibliographyRepository) {
        this.bibliographyRepository = bibliographyRepository;
    }

    @Override
    public void createBibliography(Bibliography bibliography) {
        Bibliography temp = new Bibliography();
        temp.setBibliographyId(bibliography.getBibliographyId());
        temp.setPublicationTitle(bibliography.getPublicationTitle());
        temp.setPublicationAuthor(bibliography.getPublicationAuthor());
        if (isValidPageCount(bibliography)) {
            temp.setPublicationPageCount(bibliography.getPublicationPageCount());
        } else {
            temp.setPublicationPageCount(0);
        }
        if (isValidYear(bibliography)) {
            temp.setPublicationYear(bibliography.getPublicationYear());
        } else {
            temp.setPublicationYear(0);
        }
        temp.setFiles(new ArrayList<>());
        bibliographyRepository.save(temp);
    }

    @Override
    public void addFileToBibliography(String bibliographyId, String fileName) {
        Bibliography bib = bibliographyRepository.findById(bibliographyId).orElse(null);
        bibliographyRepository.delete(bib);
        if (null == bib.getFiles()) {
            bib.setFiles(new ArrayList<>());
        }
        bib.getFiles().add(fileName);
        bibliographyRepository.save(bib);
    }

    @Override
    public void deleteFileFromBibliography(Bibliography bibliography, FileDTO file) {

    }

    @Override
    public List<Bibliography> listBibliographies() {
        List<Bibliography> result = new ArrayList<>();
        bibliographyRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public void deleteBibliography(Bibliography bibliography) {

    }

    private boolean isValidPageCount(Bibliography bibliography) {
        return bibliography.getPublicationPageCount() > 0;
    }

    private boolean isValidYear(Bibliography bibliography) {
        return bibliography.getPublicationYear() >= -3000
            && bibliography.getPublicationYear() <= 3000;
    }
}
