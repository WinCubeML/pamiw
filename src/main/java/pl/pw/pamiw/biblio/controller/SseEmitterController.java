package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pl.pw.pamiw.biblio.model.Notification;
import pl.pw.pamiw.biblio.service.NotificationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class SseEmitterController {

    @Autowired
    FileController fileController;

    @Autowired
    NotificationService notificationService;

    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @RequestMapping(value = "/notifysse", method = RequestMethod.GET)
    public SseEmitter handleSse(HttpServletRequest request, HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter(2000L);
        nonBlockingService.execute(() -> {
            try {
                emitter.send(getNotification(request, response));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    String getNotification(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity responseEntity = fileController.checkCookies(request, response);
        String sessionid = "";
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Cookie[] cookies = request.getCookies();
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("sessionid")).findAny().orElse(null);
            sessionid = user.getValue();
        } else {
            return null;
        }
        List<Notification> notifications = notificationService.getUnseenNotificationsForSessionId(sessionid);
        if (notifications == null || notifications.size() == 0)
            return null;
        for (Notification notification : notifications) {
            notificationService.setSeen(notification.getNotificationId());
        }
        return notifications.get(0).getPubName();
    }
}
