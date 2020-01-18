package pl.pw.pamiw.biblio.controller;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pw.pamiw.biblio.model.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthController {
    private final AuthenticationController authenticationController;

    @Autowired
    public AuthController(AppConfig config) {
        JwkProvider jwkProvider = new JwkProviderBuilder(config.getDomain()).build();
        authenticationController = AuthenticationController.newBuilder(config.getDomain(), config.getClientId(), config.getClientSecret())
                .withJwkProvider(jwkProvider)
                .build();
    }

    public Tokens handle(HttpServletRequest request, HttpServletResponse response) throws IdentityVerificationException {
        return authenticationController.handle(request, response);
    }

    public String buildAuthorizeUrl(HttpServletRequest request, HttpServletResponse response, String redirectUri) {
        return authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .build();
    }
}
