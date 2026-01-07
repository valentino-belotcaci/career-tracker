package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.UserService;
import VaLocaProject.Models.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class UserController{

    @Autowired
    UserService userService;

    // Returns all users
    @GetMapping("/User/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Inserts a new user
    @PostMapping("/User/insertUser/{id}")
    public ResponseEntity<User> insertUser(@PathVariable User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    
}