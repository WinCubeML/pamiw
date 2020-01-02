package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.LoginJWT;
import pl.pw.pamiw.biblio.repositories.UserRepository;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService { //TODO rest of LoginServiceImpl
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginJWT createJWT(LoginJWT jwt) {
        return null;
    }

    @Override
    public List<LoginJWT> getJWTbyLogin(String login) {
        return null;
    }

    @Override
    public void destroyJWT(String login) {

    }
}
