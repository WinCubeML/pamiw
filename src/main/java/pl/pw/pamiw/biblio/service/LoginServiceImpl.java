package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.LoginJWT;
import pl.pw.pamiw.biblio.repositories.LoginRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService { //TODO rest of LoginServiceImpl

    private LoginRepository loginRepository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public LoginJWT createJWT(LoginJWT jwt) {
        return null;
    }

    @Override
    public List<LoginJWT> getJWTbyLogin(String login) {
        List<LoginJWT> jwts = new ArrayList<>();
        loginRepository.findAllById(Collections.singleton(login)).forEach(jwts::add);
        return jwts;
    }

    @Override
    public void destroyJWT(String login) {

    }
}
