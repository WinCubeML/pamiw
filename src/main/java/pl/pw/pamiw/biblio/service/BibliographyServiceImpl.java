package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.repositories.BibliographyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Bibliography getBibliographyFromId(String bibliographyId) {
        return bibliographyRepository.findById(bibliographyId).orElse(null);
    }

    @Override
    public void deleteFileFromBibliography(String bibliographyId, String fileName) {
        Bibliography bib = bibliographyRepository.findById(bibliographyId).orElse(null);
        bibliographyRepository.delete(bib);
        if (null == bib.getFiles()) {
            bib.setFiles(new ArrayList<>());
        }
        List<String> result;
        result = bib.getFiles().stream().filter(name -> !name.equals(fileName)).collect(Collectors.toList());
        bib.setFiles(result);
        bibliographyRepository.save(bib);
    }

    @Override
    public List<Bibliography> listBibliographies() {
        List<Bibliography> result = new ArrayList<>();
        bibliographyRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public void deleteBibliography(String bibliographyId) {
        bibliographyRepository.delete(bibliographyRepository.findById(bibliographyId).orElse(null));
    }

    private boolean isValidPageCount(Bibliography bibliography) {
        return bibliography.getPublicationPageCount() > 0;
    }

    private boolean isValidYear(Bibliography bibliography) {
        return bibliography.getPublicationYear() >= -3000
            && bibliography.getPublicationYear() <= 3000;
    }
}
