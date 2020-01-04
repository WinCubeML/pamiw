package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.SessionData;

@Service
public interface LoginService { //TODO rest of LoginService interface
    SessionData createSession(SessionData sessionData);

    SessionData getSessionById(String sessionId);

    void destroySession(SessionData sessionData);

    void checkExpiredSessions();
}
