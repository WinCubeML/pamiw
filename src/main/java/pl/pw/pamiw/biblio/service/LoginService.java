package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.SessionData;

import java.util.List;

@Service
public interface LoginService {
    SessionData createSession(SessionData sessionData);

    SessionData getSessionById(String sessionId);

    List<SessionData> getAllSessions();

    void destroySession(SessionData sessionData);

    void checkExpiredSessions();
}
