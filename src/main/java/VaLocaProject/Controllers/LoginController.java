package VaLocaProject.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController{

    @GetMapping("/login")
    public String putLogin(){
        return "Login";
    }
    
}