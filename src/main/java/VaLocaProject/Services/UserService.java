package VaLocaProject.Services;

import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;


    public Optional<User> getUserById(Long id ) { // Optional as a User may not be found
        // Delegate to the repository
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    // Return saved user so callers get generated id
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}