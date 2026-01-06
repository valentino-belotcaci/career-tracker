package VaLocaProject.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.UserService;
import VaLocaProject.Models.User;

@RestController
public class UserController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/User/all")
    public String getUsername(){
        return "User";
    }
    
    @GetMapping("/User/{id}")
    // Returns the full response with headers, status and the User object
    public ResponseEntity<User> getUserById(@PathVariable Long id){  
        return ResponseEntity.of(userService.getUserById(id));
    }
    
}