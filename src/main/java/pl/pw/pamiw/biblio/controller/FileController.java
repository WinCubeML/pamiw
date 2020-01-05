package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.exceptions.ExpiredSessionException;
import pl.pw.pamiw.biblio.exceptions.ForbiddenCookieException;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class FileController { // TODO zrobić kontroler do plików
    private LoginService loginService;
    private FileService fileService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setFileServices(LoginService loginService, FileService fileService) {
        this.loginService = loginService;
        this.fileService = fileService;
    }

    private ResponseEntity checkCookies(HttpServletRequest request, HttpServletResponse response) {
        loginService.checkExpiredSessions();
        try {
            Cookie[] cookies = request.getCookies();
            if (null == cookies) {
                throw new ExpiredSessionException();
            }
            Cookie session = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("sessionid")).findAny().orElse(null);
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);
            if (null == session || null == user) {
                System.out.println("Sesja wygasła");
                throw new ExpiredSessionException();
            }

            SessionData checkSession = loginService.getSessionById(session.getValue());
            if (null == checkSession || !checkSession.getLogin().equals(user.getValue())) {
                System.out.println("Znaleziono zakazane ciasteczko");
                if (null != checkSession)
                    loginService.destroySession(checkSession);
                session.setMaxAge(0);
                user.setMaxAge(0);
                response.addCookie(session);
                response.addCookie(user);
                throw new ForbiddenCookieException();
            }
        } catch (ExpiredSessionException | ForbiddenCookieException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFilePage(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            //TODO obsługa JWT listowania
            //TODO wyświetlanie listowania plików
            return "files";
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/files/upload", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            //TODO obsługa JWT dodawania
            //TODO obsługa dodawania plików
            return "redirect:/files";
        } else {
            return "forbidden";
        }
    }
}
