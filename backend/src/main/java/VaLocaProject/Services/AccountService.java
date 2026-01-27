package VaLocaProject.Services;

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

@Service
public class AccountService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    //@Autowired
    //private RedisService redisService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    // FIX : We need to remove the cache operations 
    // from the User- and Company Service and move them 
    // here for avoiding code duplication
    //private static final Duration ACCOUNT_CACHE_TTL = Duration.ofHours(1);

    
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

    public Account getAccountByEmail(String email) {
        String key = "account:" + email;

        return Optional.ofNullable(redisService.get(key))       // 1) Try cache
                .filter(Account.class::isInstance)
                .map(Account.class::cast)
                .or(() -> userService.getUserByEmail(email)) // 2) Try user
                        .map(user -> (Account) user)
                .or(() ->companyService.getCompanyByEmail(email)) // 3) Try company
                        .map(company -> (Account) company)
                .map(account -> { 
                    // Cache the found account by creating a copy without the password, then return the original
                    Account accountCache = account;
                    accountCache.setPassword(""); // Remove password before caching
                    try {
                        redisService.save(key, accountCache, ACCOUNT_CACHE_TTL);
                    } catch (Exception ignored) {}
                    return account;
                })
                .orElseThrow(() -> new RuntimeException(
                        "Account not found with email: " + email));
    }

    public Account getAccountById(Long id) {
        String key = "accountId:" + id;

        return Optional.ofNullable(redisService.get(key))       // 1) Try cache
                .filter(Account.class::isInstance)
                .map(Account.class::cast)
                .or(() -> userService.getUserByAccountId(id) // 2) Try user
                        .map(user -> (Account) user))
                .or(() -> companyService.getCompanyByAccountId(id)) // 3) Try company
                        .map(company -> (Account) company)
                .map(account -> { // Cache the found account
                    try {
                        redisService.save(key, account, ACCOUNT_CACHE_TTL);
                    } catch (Exception ignored) {}
                    return account;
                })
                .orElseThrow(() -> new RuntimeException(
                        "Account not found with id: " + id));

    }

    // Insert a new account
    public Account insertAccount(String email, String password, String type) {
        String encoded = passwordEncoder.encode(password);

        switch (type.toUpperCase()) {
            case "USER" -> {
                User newUser = new User(email, encoded);
                return userService.insertUser(newUser);
            }
            case "COMPANY" -> {
                Company newCompany = new Company(email, encoded);
                return companyService.insertCompany(newCompany);
            }
            default -> throw new RuntimeException("Unknown account type: " + type);
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


    // Update User 
    public User updateUserAccount(Long id, User incoming) {
        // create patch object for update
        User patch = new User(null, null);
        patch.setEmail(incoming.getEmail());
        patch.setPassword(incoming.getPassword());
        patch.setDescription(incoming.getDescription());
        patch.setFirstName(incoming.getFirstName());
        patch.setLastName(incoming.getLastName());

        // call userService to perform update (handles partial update & persistence)
        User updated = userService.updateUser(id, patch);

        // return the fully merged User for profile display
        User full = new User(updated.getEmail(), null);
        full.setId(updated.getId());
        full.setPassword(updated.getPassword());
        full.setDescription(updated.getDescription());
        full.setFirstName(updated.getFirstName());
        full.setLastName(updated.getLastName());

        return full;
    }

    // Update Company 
    public Company updateCompanyAccount(Long id, Company incoming) {
        Company patch = new Company(null, null);
        patch.setEmail(incoming.getEmail());
        patch.setPassword(incoming.getPassword());
        patch.setDescription(incoming.getDescription());
        patch.setName(incoming.getName());
        patch.setCity(incoming.getCity());
        patch.setStreet(incoming.getStreet());
        patch.setNumber(incoming.getNumber());

        Company updated = companyService.updateCompany(id, patch);

        Company full = new Company(updated.getEmail(), null);
        full.setId(updated.getId());
        full.setPassword(updated.getPassword());
        full.setDescription(updated.getDescription());
        full.setName(updated.getName());
        full.setCity(updated.getCity());
        full.setStreet(updated.getStreet());
        full.setNumber(updated.getNumber());

        return full;
    }

}
