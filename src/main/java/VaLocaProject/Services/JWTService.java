package VaLocaProject.Services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String secretKey = "";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //Method that acutally generate the JWT token to be sent to the browser after login 
    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            // This token lasts 30 minutes
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .and()
            .signWith(getKey())
            .compact();
    }


    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return ""; // Placeholder implementation
    }
}
