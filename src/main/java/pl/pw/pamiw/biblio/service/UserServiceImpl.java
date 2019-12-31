package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.User;
import pl.pw.pamiw.biblio.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User s) {
        userRepository.save(s);
        return s;
    }

    @Override
    public User findByLogin(String s) {
        return userRepository.findById(s).orElse(null);
    }

    @Override
    public Iterable<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteByLogin(String s) {

    }

    @Override
    public void deleteAll() {

    }
}
