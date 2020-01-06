package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.service.BibliographyService;
import pl.pw.pamiw.biblio.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BibliographyController {
    private FileService fileService;
    private BibliographyService bibliographyService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setServices(FileService fileService, BibliographyService bibliographyService) {
        this.fileService = fileService;
        this.bibliographyService = bibliographyService;
    }

    @RequestMapping(value = "/pubs", method = RequestMethod.GET)
    public String getAllPublicationsPage(HttpServletRequest request, HttpServletResponse response) {
        return pageController.notYetImplementedPage();
    }
}
