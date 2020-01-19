package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Notification;

import java.util.List;

@Service
public interface NotificationService {
    Notification createNotification(Notification notification);

    List<Notification> getUnseenNotificationsForUserName(String userName);

    List<Notification> getUnseenNotificationsForSessionId(String sessionId);

    void setSeen(long notificationId);
}
