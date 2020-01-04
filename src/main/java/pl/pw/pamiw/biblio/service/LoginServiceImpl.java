package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.repositories.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService { //TODO rest of LoginServiceImpl

    private LoginRepository loginRepository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public SessionData createSession(SessionData sessionData) {
        return loginRepository.save(sessionData);
    }

    @Override
    public SessionData getSessionById(String sessionId) {
        return loginRepository.findById(sessionId).orElse(null);
    }

    @Override
    public void destroySession(SessionData sessionData) {
        loginRepository.delete(sessionData);
    }
}
