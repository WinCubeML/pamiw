package pl.pw.pamiw.biblio.service;

import org.springframework.stereotype.Service;

@Service
public class HomePageService {
    public String welcome() {
        return "done";
    }
}
