package VaLocaProject.Services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.RedisService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    // encode passwords on update
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private static final Duration USER_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache

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

    public User updateUser(Long id, User user) {

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


    public User getUserByAccountId(Long id){
        String key = "user:" + id;

        // try read from redis first
        try {
            Object cached = redisService.get(key);
            // If cached is of type User, put it in variable "user" and return it
            if (cached instanceof User user)  return user;
            
        } catch (Exception ignored) {}

        // fallback to DB
        return userRepository.findById(id)
        .map(user -> {
            // cache the DB result
            try {
                redisService.save(key, user, USER_CACHE_TTL);
            } catch (Exception ignored) {}
            return user;
        })
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }


    public User getUserByEmail(String email) {
        String key = "user:" + email;

        // 1) Try cache
        try {
            Object cached = redisService.get(key);
            if (cached instanceof User user) {
                return user;
            }
        } catch (Exception e) {
            System.err.println("Redis get error: " + e.getMessage());
        }

        // 2) Fetch from DB using Optional internally
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));

        // 3) Save to Redis
        try {
            redisService.save(key, user, USER_CACHE_TTL);
        } catch (Exception e) {
            System.err.println("Redis save error: " + e.getMessage());
        }

        return user;
    }



}