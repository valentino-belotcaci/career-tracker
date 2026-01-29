package VaLocaProject.Services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Security.JWTService;
import VaLocaProject.Security.RedisService;

@Service
public class AccountService {

    private final CompanyService companyService;
    private final UserService userService;
    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    // FIX : We need to remove the cache operations 
    // from the User- and Company Service and move them 
    // here for avoiding code duplication
    private static final Duration ACCOUNT_CACHE_TTL = Duration.ofHours(1);

    public AccountService(
            CompanyService companyService,
            UserService userService,
            RedisService redisService,
            BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authManager,
            JWTService jwtService
    ) {
        this.companyService = companyService;
        this.userService = userService;
        this.redisService = redisService;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }
    
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



    public Account updateAccount(Long id, UpdateAccountDTO update) {
        Account found = getAccountById(id);
        if (found instanceof User) {
            return userService.updateUser(id, update);        
        } else if (found instanceof Company) {
            return companyService.updateCompany(id, update);  
        }

        return null;
    }


}
