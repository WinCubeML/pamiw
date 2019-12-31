package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.User;

@Service
public interface UserService {
    User save(User user);

    User findByLogin(String login);

    Iterable<User> getAllUsers();

    long count();

    void deleteByLogin(String login);

    void deleteAll();
}
