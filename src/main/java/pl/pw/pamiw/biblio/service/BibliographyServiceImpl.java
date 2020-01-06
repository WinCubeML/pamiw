package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.FileDTO;
import pl.pw.pamiw.biblio.repositories.BibliographyRepository;

@Service
public class BibliographyServiceImpl implements BibliographyService {
    private BibliographyRepository bibliographyRepository;

    @Autowired
    public BibliographyServiceImpl(BibliographyRepository bibliographyRepository) {
        this.bibliographyRepository = bibliographyRepository;
    }

    @Override
    public void createBibliography(Bibliography bibliography) {

    }

    @Override
    public void addToBibliography(Bibliography bibliography, FileDTO file) {

    }

    @Override
    public void deleteFromBibliography(Bibliography bibliography, FileDTO file) {

    }

    @Override
    public void listBibliographies() {

    }

    @Override
    public void deleteBibliography(Bibliography bibliography) {

    }
}
