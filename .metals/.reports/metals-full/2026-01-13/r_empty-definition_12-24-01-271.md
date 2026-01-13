error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/UserController.java:_empty_/RequestMapping#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/UserController.java
empty definition using pc, found symbol in pc: _empty_/RequestMapping#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 877
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/UserController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.UserService;
import VaLocaProject.Models.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapp@@ing("/User")
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
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/getUserByAccountId/{id}")
    public ResponseEntity<User> getUserByAccountId(@PathVariable Long id) {
        User user = userService.getUserByAccountId(id);

        // Additional check
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/RequestMapping#