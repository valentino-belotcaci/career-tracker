package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    // encode passwords on update
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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

        if (user.getFirstName() != null) {
            foundUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            foundUser.setLastName(user.getLastName());
        }
        
        if (user.getDescription() != null) {
            foundUser.setDescription(user.getDescription());
        }

        if (user.getPassword() != null) {
            // encode password before saving
            foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        // Add other fields to update...

        // Actualy submit the new user version
        return userRepository.save(foundUser);
    }

    public User getUserByAccountId(Long id){
        return userRepository.findById(id).orElse(null);
    }


}