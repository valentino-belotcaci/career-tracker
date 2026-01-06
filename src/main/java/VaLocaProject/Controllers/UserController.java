package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.User;
import VaLocaProject.Services.UserService;


@RestController
public class UserController{

    @Autowired // to automatically inject the service instance
    UserService userService;

    @GetMapping("/User")
    public List<User> getUsername(){
        return userService.getUsers();
    }

    @GetMapping("/User2")
    public String getUsername2(){
        return "userService.getUsers()";
    }
    
}