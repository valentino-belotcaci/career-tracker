package VaLocaProject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.UserService;


@RestController
public class UserController{

    @Autowired
    UserService UserService;

    @GetMapping("/User")
    public String getUsername(){
        return "User";
    }
    
}