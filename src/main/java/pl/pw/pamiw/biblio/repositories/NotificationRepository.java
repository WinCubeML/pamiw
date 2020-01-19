package pl.pw.pamiw.biblio.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pw.pamiw.biblio.model.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {
}
