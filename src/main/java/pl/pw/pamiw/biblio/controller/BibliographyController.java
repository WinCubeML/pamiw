package pl.pw.pamiw.biblio.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.exceptions.ExpiredSessionException;
import pl.pw.pamiw.biblio.exceptions.ForbiddenCookieException;
import pl.pw.pamiw.biblio.model.Bibliography;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.service.BibliographyService;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.JWTService;
import pl.pw.pamiw.biblio.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class BibliographyController {
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

    private String createToken(String login, String claimToken) {
        return JWT.create().withIssuer("biblioapp").withClaim("user", login)
            .withClaim(claimToken, true).sign(Algorithm.HMAC256(JWT_SECRET.getBytes()));
    }

    @RequestMapping(value = "/pubs", method = RequestMethod.GET)
    public String getAllPublicationsPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Cookie[] cookies = request.getCookies();
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);

            if (jwtService.canIList(createToken(user.getValue(), "list"), user.getValue())) {
                model.addAttribute("bibs", bibliographyService.listBibliographies());
                return "pubs";
            } else {
                System.out.println("Nie udało się autoryzować JWT");
                return "forbidden";
            }
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/pubs/create", method = RequestMethod.GET)
    public String showCreatePublicationPage(Model model) {
        model.addAttribute("bibliography", new Bibliography());
        return "addpub";
    }

    @RequestMapping(value = "/pubs/create", method = RequestMethod.POST)
    public String createPublication(@ModelAttribute Bibliography bibliography) {
        bibliographyService.createBibliography(bibliography);
        return "redirect:/pubs";
    }
}
