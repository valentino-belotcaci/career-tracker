package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.User;
import VaLocaProject.Services.UserService;




@RestController
@RequestMapping("/User")
public class UserController{

    @Autowired
    UserService userService;

    // Returns all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Inserts a new user 
    @PostMapping("/insertUser")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.insertUser(user));
    }   

    // Deletes all users
    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<String> deleteAllUsers(){
       userService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted");
    }

    // Updates a user's fields
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getUserByAccountId/{id}")
    public ResponseEntity<User> getUserByAccountId(@PathVariable Long id) {
        return userService.getUserByAccountId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}

    
