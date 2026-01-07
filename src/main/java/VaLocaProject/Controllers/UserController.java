package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.UserService;
import VaLocaProject.Models.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController{

    @Autowired
    UserService userService;

    // Returns all users
    @GetMapping("/User/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Inserts a new user (send JSON body without id/version)
    @PostMapping("/User/insertUser")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        // Ensure server doesn't treat client-sent id as update
        try { java.lang.reflect.Field f = user.getClass().getDeclaredField("user_id"); f.setAccessible(true); f.set(user, null); } catch (Exception ignored) {}
        return ResponseEntity.ok(userService.saveUser(user));
    }

}