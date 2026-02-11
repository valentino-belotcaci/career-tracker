package VaLocaProject.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/* Never put passwords inside a JWT as it is 
 * Base64 ENCODED not encrypted, its easily accessible 
*/

@Service
public class JWTService {


    private String secretKey = "Ym9sZC1hbmQtYnJpdmUta2V5LXdpdGgtZW5vdWdoLWxlbmd0aC1mb3ItMjU2LWJpdHMK";

    public JWTService(){
        /* 
        // Defines the procedures to determine which algorithm use to gen the secret key and encode it 
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (Exception e) {
            // catch
        }
        */
    }
    
    // Method that acutally generate the JWT token to be sent to the browser after login 
    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>(); // A map of string and objects, used as payload of the token

        return Jwts.builder() // Initializes the builder for the JWT token
            .claims() // Selects we are adding claims, so the payload of the token
            .add(claims) // adds the claims template 
            .subject(email) // adds the user email in the claims, to identify the user when receiving the token from the client
            .issuedAt(new Date(System.currentTimeMillis())) // adds the issue date of the token, to check later if the token is expired
            // This token lasts 30 minutes
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Set token expiration time
            .and() // With these we "deselect" the claims part of the token to perform other operations on the token builder
            .signWith(getKey()) // Sign the token with the secret key, that we will use to verify the token when receiving it from the client
            .compact(); // Builds the token and returns it as a string
    }

    // Getter for the secret key
    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ----- UP UNTIL HERE IS FOR TOKEN GENERATION -----

    // To validate the token to some userDetails for Role authentication
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Check if the token id expired by comparing the expiration date with the current date
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Here we pass the Function instead of putting it here, so we can use the generic and this process can work for claims of everytype, not only strings
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Get the payload of claims from the token
        return claimsResolver.apply(claims); // Extract the specific claim we need to check
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() // Creates a parser, opposite of the builder, to parse the token and extract the claims
            .verifyWith(getKey()) // Check the key of the token
            .build() // Build the parser
            .parseSignedClaims(token) // Actually extract the claims from the token, if the key is correct, otherwise it throws an exception
            .getPayload(); // Get the payload of the token, that is the claims
    }
}
