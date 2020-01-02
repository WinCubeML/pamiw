package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.SessionData;
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
    public SessionData createSession(SessionData sessionData) {
        return null;
    }

    @Override
    public List<SessionData> getSessionByLogin(String login) {
        List<SessionData> sessionData = new ArrayList<>();
        loginRepository.findAllById(Collections.singleton(login)).forEach(sessionData::add);
        return sessionData;
    }

    @Override
    public void destroySession(String login) {

    }
}
