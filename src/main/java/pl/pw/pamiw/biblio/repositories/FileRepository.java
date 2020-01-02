package pl.pw.pamiw.biblio.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pw.pamiw.biblio.model.FileDTO;

@Repository
public interface FileRepository extends CrudRepository<FileDTO, String> {
}
