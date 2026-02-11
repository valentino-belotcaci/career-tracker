package VaLocaProject.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final CacheManager cacheManager;



    public AccountService(
            CompanyRepository companyRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authManager,
            JWTService jwtService,
            CacheManager cacheManager
    ) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.cacheManager = cacheManager;
    }

    // @Cacheable("AllAccounts")
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(userRepository.findAll());
        accounts.addAll(companyRepository.findAll());
        return accounts;
    }

    // @Cacheable("AllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // @Cacheable("AllCompanies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Caching( evict = {
        @CacheEvict(value = "accountsByEmail", allEntries = true),
        @CacheEvict(value = "accountsById", allEntries = true)})
    public void deleteAllAccounts() {
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Cacheable("accountsByEmail")
    public Account getAccountByEmail(String email) {
        // 2) Try user repository
        Account account = userRepository.findByEmail(email)
                .map(user -> (Account) user)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with email: " + email));

        // 3) Try company repository
        if (account == null) {
            account = companyRepository.findByEmail(email)
                    .map(company -> (Account) company)
                    .orElseThrow(() ->
                            new RuntimeException("Account not found with email: " + email)
                    );
        }

        // When method is called (so no cache hit), we put also in the id-based cache
        // As that method is used a lot more
        cacheManager.getCache("accounts").put(account.getId(), account);
        return account;
    }

    @Cacheable("accountsById")
    public Account getAccountById(UUID id) {
        // 1) Try user repository
        Account account = userRepository.findById(id)
                .map(user -> (Account) user)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        // 3) Try company repository
        if (account == null) {
            account = companyRepository.findById(id)
                    .map(company -> (Account) company)
                    .orElseThrow(() ->
                            new RuntimeException("Account not found with id: " + id)
                    );
        }

        return account;
    }


    @Caching( put = {
        @CachePut(value = "accountsByEmail", key = "#result.email"),
        @CachePut(value = "accountsById", key = "#result.id")})
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
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email e password obbligatorie");
        }

        // Use Spring security to authenticate
        // Authentication authentication = 
        // if we need to perform some operations on the authentication we can store it
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Return the JWT token
        return jwtService.generateToken(email);
    }


    @Caching(
        put = {
            @CachePut(value = "accountsByEmail", key = "#result.email"),
            @CachePut(value = "accountsById", key = "#result.id")})
    @Transactional
    public Account updateAccount(UUID id, UpdateAccountDTO update) {
    Account account = userRepository.findById(id).map(a -> (Account) a)
            .or(() -> companyRepository.findById(id).map(a -> (Account) a))
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        
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

        return account;
    }

}
