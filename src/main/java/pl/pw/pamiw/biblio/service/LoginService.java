package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.LoginJWT;

import java.util.List;

@Service
public interface LoginService { //TODO rest of LoginService interface
    LoginJWT createJWT(LoginJWT jwt);

    List<LoginJWT> getJWTbyLogin(String login);

    void destroyJWT(String login);
}
