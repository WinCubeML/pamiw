package pl.pw.pamiw.biblio.controller;

import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CallbackAuth0Controller {

    @Autowired
    private AuthController authController;

    @RequestMapping(value = "/callbackAuth0", method = RequestMethod.GET)
    public void getCallbackAuth0(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            Tokens tokens = authController.handle(request, response);
            SessionUtils.set(request, "accessToken", tokens.getAccessToken());
            SessionUtils.set(request, "idToken", tokens.getIdToken());
            SessionUtils.set(request, "expiresIn", tokens.getExpiresIn());
            response.sendRedirect("/filesAuth0");
        } catch (IdentityVerificationException e) {
            System.out.println("Nie udało się połączyć z Auth0. Spróbuj się zalogować tradycyjnie");
            e.printStackTrace();
            response.sendRedirect("/login");
        }
    }
}
