package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.logic.Session;
import pl.pw.pamiw.biblio.model.LoginDTO;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.model.User;
import pl.pw.pamiw.biblio.service.LoginService;
import pl.pw.pamiw.biblio.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
public class LoginController {
    private UserService userService;
    private LoginService loginService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setLoginServices(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request) {
        loginService.checkExpiredSessions();
        try {
            Cookie[] cookies = request.getCookies();
            Cookie session = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("sessionid")).findAny().orElse(null);
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);
            if (null == session || null == user) {
                System.out.println("Ciasteczka nie znaleziono");
                throw new IllegalStateException();
            }

            SessionData checkSession = loginService.getSessionById(session.getValue());
            if (null != checkSession && checkSession.getLogin().equals(user.getValue())) {
                System.out.println("Ciasteczko znalezione w Redis");
                return "redirect:/files";
            } else {
                System.out.println("Ciasteczka nie znaleziono w Redis lub login nie pokrywa się z danymi sesji");
                loginService.destroySession(checkSession);
                session.setMaxAge(0);
                user.setMaxAge(0);
                throw new IllegalStateException();
            }
        } catch (IllegalStateException | NullPointerException e) {
            model.addAttribute("loginDTO", new LoginDTO());
            return "login";
        }
    }

    @RequestMapping(value = "/login/authorize", method = RequestMethod.POST)
    public String authorizeLogin(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response, HttpServletRequest request) {
        loginService.checkExpiredSessions();

        User user = userService.findByLogin(loginDTO.getLogin());
        StringBuilder sb = new StringBuilder();
        sb.append(loginDTO.getPassword()).append(',').append(loginDTO.getPassword()); // TODO Wtf ten bypass
        if (null != user && user.getPassword().equals(sb.toString())) {
            int cookieMaxAge = 3 * 60;

            Cookie userCookie = new Cookie("user", loginDTO.getLogin());
            userCookie.setMaxAge(cookieMaxAge);
            userCookie.setHttpOnly(true);
            userCookie.setPath("/");
            response.addCookie(userCookie);

            String sessionId = Session.createSessionIdForCookie();
            Cookie sessionCookie = new Cookie("sessionid", sessionId);
            sessionCookie.setMaxAge(cookieMaxAge);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);

            SessionData sessionDataDTO = new SessionData();
            sessionDataDTO.setLogin(loginDTO.getLogin());
            sessionDataDTO.setSessionId(sessionId);
            sessionDataDTO.setExpiryDate(LocalDateTime.now().plusSeconds(cookieMaxAge).toString());
            loginService.createSession(sessionDataDTO);

            System.out.println("Zalogowano");
            return "redirect:/files";
        } else {
            System.out.println("Nie udało się zalogować");
            return "badlogin";
        }
    }
}
