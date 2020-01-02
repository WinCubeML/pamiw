package pl.pw.pamiw.biblio.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginJWT {
    private String login;
    private String JWT;
}
