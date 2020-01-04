package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.logic.Session;
import pl.pw.pamiw.biblio.model.LoginDTO;
import pl.pw.pamiw.biblio.model.User;
import pl.pw.pamiw.biblio.service.LoginService;
import pl.pw.pamiw.biblio.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        try {
            Cookie[] cookies = request.getCookies();
            Cookie session = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("sessionid")).findAny().orElse(null);
            if (null == session) {
                System.out.println("Ciasteczka nie znaleziono");
                throw new IllegalStateException();
            }
            //TODO sprawdzenie ciasteczka w redisie a potem ewentualne przekierowanie
            return "redirect:/files";
        } catch (IllegalStateException | NullPointerException e) {
            model.addAttribute("loginDTO", new LoginDTO());
            return "login";
        }
    }

    @RequestMapping(value = "/login/authorize", method = RequestMethod.POST)
    public String authorizeLogin(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.findByLogin(loginDTO.getLogin());
        StringBuilder sb = new StringBuilder();
        sb.append(loginDTO.getPassword()).append(',').append(loginDTO.getPassword()); // TODO Wtf ten bypass
        if (null != user && user.getPassword().equals(sb.toString())) {
            Cookie userCookie = new Cookie("user", loginDTO.getLogin());
            userCookie.setMaxAge(3 * 60);
            userCookie.setHttpOnly(true);
            userCookie.setPath("/");
            response.addCookie(userCookie);

            String sessionId = Session.createSessionIdForCookie();
            Cookie sessionCookie = new Cookie("sessionid", sessionId);
            sessionCookie.setMaxAge(3 * 60);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            //TODO dodanie ciasteczka do redis
            System.out.println("Zalogowano");
            return "redirect:/files";
        } else {
            System.out.println("Nie zalogowano bo mnie nie ma albo coś innego");
        }
        return pageController.notYetImplementedPage();
    }
}
