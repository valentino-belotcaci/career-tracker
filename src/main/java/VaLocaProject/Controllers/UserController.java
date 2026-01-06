package VaLocaProject.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController{

    @GetMapping("/User")
    public String getString(){
        return "User";
    }
    
}