package pl.pw.pamiw.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pw.pamiw.biblio.model.Notification;
import pl.pw.pamiw.biblio.repositories.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnseenNotificationsForUserName(String userName) {
        List<Notification> notifications = (List<Notification>) notificationRepository.findAll();
        notifications = notifications.stream()
                .filter(notification -> notification.getUserName().equals(userName) && !notification.isSeen())
                .collect(Collectors.toList());
        return notifications;
    }

    @Override
    public List<Notification> getUnseenNotificationsForSessionId(String sessionId) {
        List<Notification> notifications = (List<Notification>) notificationRepository.findAll();
        notifications = notifications.stream()
                .filter(notification -> notification.getSessionId().equals(sessionId) && !notification.isSeen())
                .collect(Collectors.toList());
        return notifications;
    }

    @Override
    public void setSeen(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification == null)
            return;
        notificationRepository.delete(notification);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }
}
