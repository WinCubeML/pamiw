package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.model.LoginDTO;
import pl.pw.pamiw.biblio.model.User;
import pl.pw.pamiw.biblio.service.LoginService;
import pl.pw.pamiw.biblio.service.UserService;

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
    public String loginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @RequestMapping(value = "/login/authorize", method = RequestMethod.POST)
    public String authorizeLogin(@ModelAttribute LoginDTO loginDTO) {
        User user = userService.findByLogin(loginDTO.getLogin());
        StringBuilder sb = new StringBuilder();
        sb.append(loginDTO.getPassword()).append(',').append(loginDTO.getPassword()); // TODO Wtf ten bypass
        if (null != user && user.getPassword().equals(sb.toString())) {
            System.out.println("Zalogowano");
        } else {
            System.out.println("Nie zalogowano bo mnie nie ma albo coś innego");
        }
        return pageController.notYetImplementedPage();
    }
}
