package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.SessionData;

import java.util.List;

@Service
public interface LoginService { //TODO rest of LoginService interface
    SessionData createSession(SessionData sessionData);

    List<SessionData> getSessionByLogin(String login);

    void destroySession(String login);
}
