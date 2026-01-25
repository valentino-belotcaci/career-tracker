package VaLocaProject.Services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.User;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.RedisService;


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

    public Optional<List<User>> getAllUsers(){
        return Optional.of(userRepository.findAll());
    }

    // Return saved user so callers get generated id
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public Optional<User> updateUser(Long id, User user){

        return userRepository.findById(id).map(foundUser -> {
            // Check if not null to not update some fields as null
            if (user.getEmail() != null) foundUser.setEmail(user.getEmail());
            if (user.getFirstName() != null) foundUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) foundUser.setLastName(user.getLastName());
            if (user.getDescription() != null) foundUser.setDescription(user.getDescription()); 
            // encode password before saving
            if (user.getPassword() != null) foundUser.setPassword(passwordEncoder.encode(user.getPassword()));

            // Actualy submit the new user version
            return userRepository.save(foundUser);
        });

    }

    public Optional<User> getUserByAccountId(Long id){
        String key = "user:" + id;

        // try read from redis first
        try {
            Object cached = redisService.get(key);
            // If cached is of type User, put it in variable "user" and return it
            if (cached instanceof User user)  return Optional.of(user);
            
        } catch (Exception ignored) {}

        // fallback to DB
        Optional<User> user = userRepository.findById(id);


        // cache the DB result if found
        if (user.isPresent()) {
            try {
                redisService.save(key, user, USER_CACHE_TTL);
            } catch (Exception ignored) {}
        }

        return user;
    }

    public Optional<User> getUserByEmail(String email){
        String key = "user:" + email;

        try {
            Object cached = redisService.get(key);
            // If cached is of type User, put it in variable "user" and return it
            if (cached instanceof User user) return Optional.of(user);
        } catch (Exception e) {
        }

        // ofNullable let create a Optional like of, but is safer as it allows null 
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));


        if (user.isPresent()){
            try {
                redisService.save(key, user, USER_CACHE_TTL);
            } catch (Exception e) {
            }
        }

        return user;
    };

}