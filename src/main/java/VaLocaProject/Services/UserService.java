package VaLocaProject.Services;

import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;


    public Optional<User> getUserById(Long id ) { // Optional as a User may not be found
        // Delegate to the repository
        return userRepository.getUserById(id);
    }

    public void insertUser(User user) {
        userRepository.save(user); // Save used for JPA repository to add a user
    }
}