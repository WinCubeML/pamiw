package pl.pw.pamiw.biblio.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public boolean canIList(String token, String login) {
        try {
            Key key = new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA256");
            Jwts.parser()
                    .require("iss", "biblioapp")
                    .require("user", login)
                    .require("list", true)
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            System.out.println("Zły token JWT");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean canIDownload(String token, String login) {
        try {
            Key key = new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA256");
            Jwts.parser()
                    .require("iss", "biblioapp")
                    .require("user", login)
                    .require("download", true)
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            System.out.println("Zły token JWT");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean canIUpload(String token, String login) {
        try {
            Key key = new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA256");
            Jwts.parser()
                    .require("iss", "biblioapp")
                    .require("user", login)
                    .require("upload", true)
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            System.out.println("Zły token JWT");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean canIDelete(String token, String login) {
        try {
            Key key = new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA256");
            Jwts.parser()
                    .require("iss", "biblioapp")
                    .require("user", login)
                    .require("delete", true)
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            System.out.println("Zły token JWT");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
