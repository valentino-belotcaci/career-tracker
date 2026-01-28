package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;


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

    public User updateUser(Long id, UpdateAccountDTO user) {

        User foundUser = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        // Update fields only if non-null
        if (user.getEmail() != null) foundUser.setEmail(user.getEmail());
        if (user.getFirstName() != null) foundUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) foundUser.setLastName(user.getLastName());
        if (user.getDescription() != null) foundUser.setDescription(user.getDescription());
        if (user.getPassword() != null)
            foundUser.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save updated user
        return userRepository.save(foundUser);
    }


    public Optional<User> getUserByAccountId(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
       

}