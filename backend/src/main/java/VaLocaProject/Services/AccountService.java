package VaLocaProject.Services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;
import VaLocaProject.Security.RedisService;
import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    // FIX : We need to remove the cache operations 
    // from the User- and Company Service and move them 
    // here for avoiding code duplication
    private static final Duration ACCOUNT_CACHE_TTL = Duration.ofHours(1);

    public AccountService(
            CompanyRepository companyRepository,
            UserRepository userRepository,
            RedisService redisService,
            BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authManager,
            JWTService jwtService
    ) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    // Helper method to create a safe copy of Account for caching
    private Account createSafeCacheCopy(Account account) {
        Account safeCopy;

        // Use the cast in the if statement to access specific getters and setters
        if (account instanceof User user) {
            User userCopy = new User(user.getId());
            userCopy.setFirstName(user.getFirstName());
            userCopy.setLastName(user.getLastName());
            safeCopy = userCopy;
        } else if (account instanceof Company company) {
            Company companyCopy = new Company(company.getId());
            companyCopy.setCompanyName(company.getCompanyName());
            companyCopy.setCity(company.getCity());
            companyCopy.setStreet(company.getStreet());
            companyCopy.setNumber(company.getNumber());
            safeCopy = companyCopy;
        } else {
            throw new IllegalArgumentException("Unknown Account subtype: " + account.getClass());
        }

        // common fields
        safeCopy.setEmail(account.getEmail());
        safeCopy.setDescription(account.getDescription());
        // password intentionally NOT copied

        return safeCopy;
    }

    // Helper method that invalidates saved account in cache
    // for both email- and id-based accounts
    public void invalidateAccountCache(Long id, String email) {
        String idKey = "account:" + id;
        String emailKey = "account:" + email;
        try { 
            redisService.delete(idKey);
            redisService.delete(emailKey);
        } catch (Exception ignored) {}

    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(userRepository.findAll());
        accounts.addAll(companyRepository.findAll());
        return accounts;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public void deleteAllAccounts() {
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public Account getAccountByEmail(String email) {
        String key = "account:" + email;

        // 1) Try cache
        Object cached = redisService.get(key);
        if (cached instanceof Account account) {
            return account;
        }

        // 2) Try user repository
        Account account = userRepository.findByEmail(email)
                .map(user -> (Account) user)
                .orElse(null);

        // 3) Try company repository
        if (account == null) {
            account = companyRepository.findByEmail(email)
                    .map(company -> (Account) company)
                    .orElseThrow(() ->
                            new RuntimeException("Account not found with email: " + email)
                    );
        }

        // 4) Cache SAFE copy (never mutate managed entity)
        try {
            Account cacheCopy = createSafeCacheCopy(account);
            redisService.save(key, cacheCopy, ACCOUNT_CACHE_TTL);
        } catch (Exception ignored) {}

        return account;
    }


    public Account getAccountById(Long id) {
        String key = "account:" + id;

        // 1) Try cache
        Object cached = redisService.get(key);
        if (cached instanceof Account account) {
            return account;
        }

        // 2) Try user repository
        Account account = userRepository.findById(id)
                .map(user -> (Account) user)
                .orElse(null);

        // 3) Try company repository
        if (account == null) {
            account = companyRepository.findById(id)
                    .map(company -> (Account) company)
                    .orElseThrow(() ->
                            new RuntimeException("Account not found with id: " + id)
                    );
        }

        // 4) Cache SAFE copy (never mutate managed entity)
        try {
            Account cacheCopy = createSafeCacheCopy(account);
            redisService.save(key, cacheCopy, ACCOUNT_CACHE_TTL);
        } catch (Exception ignored) {}

        return account;
    }

    // This method gets account by id but doesn't use cache,
    // needed because in the update method, if the instance 
    // comes from redis and not JPA, 
    // the updates don't persist in the DB
    private Account getAccountByIdFromDb(Long id) {
        return userRepository.findById(id)
            .map(user -> (Account) user)
            .orElseGet(() ->
                companyRepository.findById(id)
                .map(company -> (Account) company)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id)));
    }


    // Insert a new account
    public Account insertAccount(String email, String password, String type) {
        String encoded = passwordEncoder.encode(password);

        switch (type.toUpperCase()) {
            case "USER" -> {
                User newUser = new User(email, encoded);
                return userRepository.save(newUser);
            }
            case "COMPANY" -> {
                Company newCompany = new Company(email, encoded);
                return companyRepository.save(newCompany);
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



    @Transactional
    public Account updateAccount(Long id, UpdateAccountDTO update) {
        Account account = getAccountByIdFromDb(id);

        // Common fields
        if (update.getEmail() != null) account.setEmail(update.getEmail());
        if (update.getDescription() != null) account.setDescription(update.getDescription());
        if (update.getPassword() != null) account.setPassword(passwordEncoder.encode(update.getPassword()));

        // Type-specific updates
        if (account instanceof User user) {
            if (update.getFirstName() != null) user.setFirstName(update.getFirstName());
            if (update.getLastName() != null) user.setLastName(update.getLastName());
        } else if (account instanceof Company company) {
            if (update.getCompanyName() != null) company.setCompanyName(update.getCompanyName());
            if (update.getCity() != null) company.setCity(update.getCity());
            if (update.getStreet() != null) company.setStreet(update.getStreet());
            if (update.getNumber() != null) company.setNumber(update.getNumber());
        }
        invalidateAccountCache(id, account.getEmail());

        return account;
    }


}
