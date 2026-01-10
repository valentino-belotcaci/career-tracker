error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/UserService.java:_empty_/UserRepository#getUserByAccountId#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/UserService.java
empty definition using pc, found symbol in pc: _empty_/UserRepository#getUserByAccountId#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1419
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/UserService.java
text:
```scala
package VaLocaProject.Services;

import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Return saved user so callers get generated id
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public User updateUser(Long id, User user){
        User foundUser = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found"));

        // Check if not null to not update some fields as null
        if (user.getEmail() != null) {
            foundUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            foundUser.setName(user.getName());
        }
        
        if (user.getDescription() != null) {
            foundUser.setDescription(user.getDescription());
        }
        // Add other fields to update...

        // Actualy submit the new user version
        return userRepository.save(foundUser);
    }

    public User getUserByAccountId(Long id){
        return userRepository.@@getUserByAccountId(id);
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/UserRepository#getUserByAccountId#