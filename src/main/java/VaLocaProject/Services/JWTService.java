package VaLocaProject.Services;

import org.springframework.stereotype.Service;

@Service
public class JWTService {
    
    //Method that acutally generate the JWT token to be sent to the browser after login 
    public String generateToken(){
        return "boh";
    }
}
