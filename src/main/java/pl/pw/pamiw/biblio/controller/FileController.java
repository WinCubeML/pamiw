package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.LoginService;

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

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFiles() {
        return pageController.notYetImplementedPage();
    }
}
