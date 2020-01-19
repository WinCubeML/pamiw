package pl.pw.pamiw.biblio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pw.pamiw.biblio.model.AppConfig;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutAuth0Controller {
    private final String domain;
    private final String clientId;

    public LogoutAuth0Controller(AppConfig config) {
        domain = config.getDomain();
        clientId = config.getClientId();
    }

    @RequestMapping(value = "/logoutAuth", method = RequestMethod.GET)
    public String logoutViaAuth0(HttpServletRequest request) {
        invalidateSession(request);

        String returnTo = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/logout";
        String logoutUrl = String.format("https://%s/v2/logout?client_id=%s&returnTo=%s", domain, clientId, returnTo);

        return "redirect:" + logoutUrl;
    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }
}

// Wykorzystany przykładowy kod dostarczony przez Auth0 (auth0-samples)
