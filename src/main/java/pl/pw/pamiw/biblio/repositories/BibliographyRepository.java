package pl.pw.pamiw.biblio.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pw.pamiw.biblio.model.Bibliography;

@Repository
public interface BibliographyRepository extends CrudRepository<Bibliography, String> {
}
