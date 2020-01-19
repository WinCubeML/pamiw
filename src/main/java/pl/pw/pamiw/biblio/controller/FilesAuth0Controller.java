package pl.pw.pamiw.biblio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import pl.pw.pamiw.biblio.exceptions.FileNameAlreadyTakenException;
import pl.pw.pamiw.biblio.model.UserForFileDTO;
import pl.pw.pamiw.biblio.service.FileService;
import pl.pw.pamiw.biblio.service.JWTService;
import pl.pw.pamiw.biblio.service.LoginService;
import pl.pw.pamiw.biblio.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FilesAuth0Controller {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private UserService userService;
    private LoginService loginService;
    private FileService fileService;
    private JWTService jwtService;

    @Autowired
    PageController pageController;

    @Autowired
    public void setFileServices(UserService userService, LoginService loginService, FileService fileService, JWTService jwtService) {
        this.userService = userService;
        this.loginService = loginService;
        this.fileService = fileService;
        this.jwtService = jwtService;
    }

    @RequestMapping(value = "/filesAuth0", method = RequestMethod.GET)
    public String getFilePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("files", fileService.listAllFiles());
        return "filesAuth";
    }

    @RequestMapping(value = "/filesAuth0/upload", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MultipartFile file) {
        try {
            if (fileService.isFileNameTaken(file.getOriginalFilename())) {
                throw new FileNameAlreadyTakenException();
            }
            fileService.uploadFile(new UserForFileDTO("Auth0", "User"), file);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
            return "redirect:/filesAuth0";
        } catch (FileNameAlreadyTakenException e) {
            System.out.println("Nazwa pliku zajęta");
            return "redirect:/filesAuth0";
        }
        return "redirect:/filesAuth0";
    }

    @RequestMapping(value = "/filesAuth0/download/{fileName}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {
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

    @RequestMapping(value = "/filesAuth0/delete/{filename}", method = RequestMethod.POST)
    public String deleteFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
        fileService.deleteFile(filename);
        return "redirect:/filesAuth0";
    }
}
