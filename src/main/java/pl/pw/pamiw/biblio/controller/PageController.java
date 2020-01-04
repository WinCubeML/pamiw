package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.service.HomePageService;
import pl.pw.pamiw.biblio.service.LoginService;
import pl.pw.pamiw.biblio.service.UserService;

@Controller
public class PageController {
    UserService userService;
    LoginService loginService;

    @Autowired
    HomePageService homePageService;

    @Autowired
    public void setServices(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage(Model model) {
        loginService.checkExpiredSessions();
        System.out.println(homePageService.welcome());
        model.addAttribute("name", "Imie");
        model.addAttribute("users", userService.getAllUsers());

        return "welcome";
    }

    @RequestMapping(value = "/notyetimplemented", method = RequestMethod.GET)
    public String notYetImplementedPage() {
        return "notyetimplemented";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String pageNotFound(Model model) {
        return "error";
    }
}
