package pl.pw.pamiw.biblio.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import pl.pw.pamiw.biblio.exceptions.FileNameAlreadyTakenException;
import pl.pw.pamiw.biblio.exceptions.ForbiddenCookieException;
import pl.pw.pamiw.biblio.model.Notification;
import pl.pw.pamiw.biblio.model.SessionData;
import pl.pw.pamiw.biblio.model.User;
import pl.pw.pamiw.biblio.model.UserForFileDTO;
import pl.pw.pamiw.biblio.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class FileController {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private UserService userService;
    private LoginService loginService;
    private FileService fileService;
    private JWTService jwtService;
    private NotificationService notificationService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setFileServices(UserService userService, LoginService loginService, FileService fileService, JWTService jwtService, NotificationService notificationService) {
        this.userService = userService;
        this.loginService = loginService;
        this.fileService = fileService;
        this.jwtService = jwtService;
        this.notificationService = notificationService;
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

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFilePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Cookie[] cookies = request.getCookies();
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);

            if (jwtService.canIList(createToken(user.getValue(), "list"), user.getValue())) {
                List<Notification> notifications = notificationService.getUnseenNotificationsForUserName(user.getValue());
                for (Notification notification : notifications)
                    System.out.println(notification.getPubName());

                model.addAttribute("files", fileService.listAllFiles());
                return "files";
            } else {
                System.out.println("Nie udało się autoryzować JWT");
                return "forbidden";
            }
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/files/upload", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MultipartFile file) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Cookie[] cookies = request.getCookies();
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);
            if (jwtService.canIUpload(createToken(user.getValue(), "upload"), user.getValue())) {
                try {
                    if (fileService.isFileNameTaken(file.getOriginalFilename())) {
                        throw new FileNameAlreadyTakenException();
                    }
                    User userDTO = userService.findByLogin(user.getValue());
                    fileService.uploadFile(new UserForFileDTO(userDTO.getName(), userDTO.getSurname()), file);
                } catch (IOException e) {
                    System.out.println("error");
                    e.printStackTrace();
                    return "redirect:/files";
                } catch (FileNameAlreadyTakenException e) {
                    System.out.println("Nazwa pliku zajęta");
                    return "redirect:/files";
                }
            }
            return "redirect:/files";
        } else {
            return "forbidden";
        }
    }

    @RequestMapping(value = "/files/download/{fileName}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return new HttpEntity(HttpStatus.FORBIDDEN);
        }
        Cookie[] cookies = request.getCookies();
        Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);
        if (jwtService.canIDownload(createToken(user.getValue(), "download"), user.getValue())) {
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
        } else {
            return new HttpEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/files/delete/{filename}", method = RequestMethod.POST)
    public String deleteFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
        ResponseEntity responseEntity = checkCookies(request, response);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Cookie[] cookies = request.getCookies();
            Cookie user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findAny().orElse(null);

            if (jwtService.canIDelete(createToken(user.getValue(), "delete"), user.getValue())) {
                fileService.deleteFile(filename);
                return "redirect:/files";
            } else {
                System.out.println("Nie udało się autoryzować JWT");
                return "forbidden";
            }
        } else {
            return "forbidden";
        }
    }
}
