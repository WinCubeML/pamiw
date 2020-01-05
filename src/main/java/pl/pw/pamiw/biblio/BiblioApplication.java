package pl.pw.pamiw.biblio;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication(scanBasePackages = {
        "pl.pw.pamiw.biblio.controller", "pl.pw.pamiw.biblio.model", "pl.pw.pamiw.biblio.repositories", "pl.pw.pamiw.biblio.service", "pl.pw.pamiw.biblio.logic"
})
public class BiblioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiblioApplication.class, args);
    }

}
