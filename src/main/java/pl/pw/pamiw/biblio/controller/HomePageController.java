package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.service.HomePageService;
import pl.pw.pamiw.biblio.service.UserService;

@Controller
public class HomePageController {
    UserService userService;

    @Autowired
    HomePageService homePageService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage(Model model) {
        System.out.println(homePageService.welcome());
        model.addAttribute("name", "Imie");
        model.addAttribute("users", userService.getAllUsers());

        return "welcome";
    }
}
