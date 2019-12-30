package pl.pw.pamiw.biblio.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pw.pamiw.biblio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
