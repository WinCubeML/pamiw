package pl.pw.pamiw.biblio.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import pl.pw.pamiw.biblio.controller.Auth0Filter;

@Component
@Configuration
public class AppConfig {
    @Value(value = "${com.auth0.domain}")
    private String domain;

    @Value(value = "${com.auth0.clientId}")
    private String clientId;

    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Auth0Filter());
        registration.addUrlPatterns("/filesAuth0/*", "/pubsAuth0/*");
        registration.setName(Auth0Filter.class.getSimpleName());
        return registration;
    }

    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}

// Wykorzystany przykładowy kod dostarczony przez Auth0 (auth0-samples)
