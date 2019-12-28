package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/notyetimplemented", method = RequestMethod.GET)
    public String notYetImplementedPage() {
        return "notyetimplemented";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        System.out.println(userService.notYetImplemented());
        return notYetImplementedPage();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpPage() {
        System.out.println(userService.notYetImplemented());
        return notYetImplementedPage();
    }
}
