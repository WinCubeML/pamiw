package pl.pw.pamiw.biblio;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Log
@SpringBootApplication(scanBasePackages = {
        "pl.pw.pamiw.biblio.controller", "pl.pw.pamiw.biblio.model", "pl.pw.pamiw.biblio.repositories", "pl.pw.pamiw.biblio.service", "pl.pw.pamiw.biblio.logic"
})
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:auth0.properties")
})
public class BiblioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiblioApplication.class, args);
    }

}
