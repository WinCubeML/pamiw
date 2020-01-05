package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import pl.pw.pamiw.biblio.exceptions.ExpiredSessionException;
import pl.pw.pamiw.biblio.exceptions.ForbiddenCookieException;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.model.UserForFileDTO;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class FileController {
    private LoginService loginService;
    private FileService fileService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setFileServices(LoginService loginService, FileService fileService) {
        this.loginService = loginService;
        this.fileService = fileService;
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

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFilePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            //TODO obsługa JWT listowania
            model.addAttribute("files", fileService.listAllFiles());
            return "files";
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/files/upload", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MultipartFile file) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            //TODO obsługa JWT dodawania
            try {
                fileService.uploadFile(new UserForFileDTO("test", "test"), file);
            } catch (IOException e) {
                System.out.println("error");
                e.printStackTrace();
                return "redirect:/files";
            }
            return "redirect:/files";
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/files/download/{fileName}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) {
        byte[] file;
        try {
            file = fileService.downloadFile(fileName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new HttpEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new HttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.replace(" ", "_"));
        headers.setContentLength(file.length);
        return new HttpEntity<>(file, headers);
    }
}
