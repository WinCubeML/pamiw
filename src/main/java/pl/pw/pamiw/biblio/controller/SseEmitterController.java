package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class SseEmitterController {

    @Autowired
    FileController fileController;

    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @RequestMapping(value = "/files/notifysse", method = RequestMethod.GET)
    public SseEmitter handleSse(HttpServletRequest request, HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                emitter.send(fileController.getNotification(request, response));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}
