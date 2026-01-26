package VaLocaProject.Services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Security.JWTService;
import VaLocaProject.Security.RedisService;

@Service
public class AccountService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private static final Duration ACCOUNT_CACHE_TTL = Duration.ofHours(1);

    
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(userService.getAllUsers());
        accounts.addAll(companyService.getAllCompanies());
        return accounts;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    public void deleteAllAccounts() {
        userService.deleteAllUsers();
        companyService.deleteAllCompanies();
    }

    // Get account by email (User or Company) and throw if not found
    public Account getAccountByEmail(String email) {
        return Optional.ofNullable(userService.getUserByEmail(email)) // wraps User
                .map(user -> (Account) user)                          // casts User to Account
                .or(() -> Optional.ofNullable(companyService.getCompanyByEmail(email)) // wraps Company
                        .map(company -> (Account) company))           // casts Company to Account
                .orElseThrow(() -> new RuntimeException("Account not found for email: " + email));
    }


    //  Insert a new account 
    public Account insertAccount(String email, String password, String type) {
        String encoded = passwordEncoder.encode(password);

        if ("USER".equalsIgnoreCase(type)) {
            User newUser = new User(email, encoded);
            return userService.insertUser(newUser);
        } else {
            Company newCompany = new Company(email, encoded);
            return companyService.insertCompany(newCompany);
        }
    }

   public String authenticate(String email, String password) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials for email: " + email);
        }

        return jwtService.generateToken(email);
    }


}
