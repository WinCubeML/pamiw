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
        if (isValidBibliography(bibliography)) {
            temp.setPublicationPageCount(bibliography.getPublicationPageCount());
            temp.setPublicationYear(bibliography.getPublicationYear());
        }
        bibliographyRepository.save(bibliography);
    }

    @Override
    public void editBibliography(Bibliography oldB, Bibliography newB) {

    }

    @Override
    public void addFileToBibliography(Bibliography bibliography, FileDTO file) {

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

    private boolean isValidBibliography(Bibliography bibliography) {
        if (bibliography.getPublicationPageCount() < 0)
            return false;
        if (bibliography.getPublicationYear() < -3000 || bibliography.getPublicationYear() > 3000)
            return false;
        return true;
    }
}
