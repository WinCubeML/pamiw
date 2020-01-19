package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.FileDTO;
import pl.pw.pamiw.biblio.service.BibliographyService;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.JWTService;
import pl.pw.pamiw.biblio.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BibliographyAuth0Controller {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private JWTService jwtService;
    private LoginService loginService;
    private FileService fileService;
    private BibliographyService bibliographyService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setServices(JWTService jwtService, LoginService loginService, FileService fileService, BibliographyService bibliographyService) {
        this.jwtService = jwtService;
        this.loginService = loginService;
        this.fileService = fileService;
        this.bibliographyService = bibliographyService;
    }

    @RequestMapping(value = "/pubsAuth0", method = RequestMethod.GET)
    public String getAllPublicationsPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("bibs", bibliographyService.listBibliographies());
        return "pubsAuth";
    }

    @RequestMapping(value = "/pubsAuth0/create", method = RequestMethod.GET)
    public String showCreatePublicationPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("bibliography", new Bibliography());
        return "addpubAuth";
    }

    @RequestMapping(value = "/pubsAuth0/create", method = RequestMethod.POST)
    public String createPublication(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Bibliography bibliography) {
        bibliographyService.createBibliography(bibliography);
        return "redirect:/pubsAuth0";
    }

    @RequestMapping(value = "/pubsAuth0/attach/{publicationId}", method = RequestMethod.GET)
    public String attachFileToPublication(HttpServletRequest request, HttpServletResponse response, @PathVariable String publicationId, Model model) {
        Bibliography bibliography = bibliographyService.getBibliographyFromId(publicationId);
        List<String> filesInPub = bibliography.getFiles();
        if (null == filesInPub)
            filesInPub = new ArrayList<>();
        List<FileDTO> files = fileService.listAllFiles();
        List<String> names = new ArrayList<>();
        for (FileDTO file : files) {
            if (!filesInPub.contains(file.getFileName()))
                names.add(file.getFileName());
        }
        model.addAttribute("fileNames", names);
        model.addAttribute("pubId", publicationId);
        return "attachtopubAuth";
    }

    @RequestMapping(value = "/pubsAuth0/attach/{publicationId}/{filename}", method = RequestMethod.GET)
    public String attachFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String publicationId, @PathVariable String filename) {
        bibliographyService.addFileToBibliography(publicationId, filename);
        return "redirect:/pubsAuth0";
    }

    @RequestMapping(value = "/pubsAuth0/detach/{publicationId}", method = RequestMethod.GET)
    public String detachFileFromPublication(HttpServletRequest request, HttpServletResponse response, @PathVariable String publicationId, Model model) {
        List<String> names = bibliographyService.getBibliographyFromId(publicationId).getFiles();
        model.addAttribute("fileNames", names);
        model.addAttribute("pubId", publicationId);
        return "detachfrompubAuth";
    }

    @RequestMapping(value = "/pubsAuth0/detach/{publicationId}/{filename}", method = RequestMethod.GET)
    public String detachFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String publicationId, @PathVariable String filename) {
        bibliographyService.deleteFileFromBibliography(publicationId, filename);
        return "redirect:/pubsAuth0";
    }

    @RequestMapping(value = "/pubsAuth0/delete/{publicationId}", method = RequestMethod.POST)
    public String deletePublication(HttpServletRequest request, HttpServletResponse response, @PathVariable String publicationId) {
        bibliographyService.deleteBibliography(publicationId);
        return "redirect:/pubsAuth0";
    }
}
