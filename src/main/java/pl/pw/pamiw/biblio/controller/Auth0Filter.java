package pl.pw.pamiw.biblio.controller;

import com.auth0.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Auth0Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String accessToken = (String) SessionUtils.get(req, "accessToken");
        String idToken = (String) SessionUtils.get(req, "idToken");
        if (accessToken == null && idToken == null) {
            res.sendRedirect("/loginAuth0");
            return;
        }
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

// Wykorzystany przykładowy kod dostarczony przez Auth0 (auth0-samples)
