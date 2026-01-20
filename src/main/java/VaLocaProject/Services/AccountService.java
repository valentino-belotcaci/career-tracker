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
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;

@Service
public class AccountService {
    

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    // Injection of password encoder
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        // addAll accepts Collection<? extends Account>
        accounts.addAll(userRepository.findAll());
        accounts.addAll(companyRepository.findAll());
        return accounts;
    }

    // Return concrete lists when callers need subclass types
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public void deleteAllAccounts(){
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public Optional<Account> getAccountByEmail(String email){
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return Optional.of(user);
        }

        Company company = companyRepository.findByEmail(email);
        if (company != null) {
            return Optional.of(company);
        }

        return Optional.empty();
    }

    // Logic for insertion of User and Company instances to the db
    public Account insertAccount(String email, String password, String type) {
        String encoded = passwordEncoder.encode(password);

        if ("USER".equalsIgnoreCase(type)) {
            User newUser = new User(email, encoded);
            return userRepository.save(newUser);
        } else {
            Company newCompany = new Company(email, encoded);
            return companyRepository.save(newCompany);
        }
    }




    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
       
        if (user == null) {
            Company company = companyRepository.findByEmail(email);

            if (company == null) {
                return null;
            }
        }


            
        // 1) Authenticate with RAW password
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        if (!authentication.isAuthenticated()) {
            return null;
        }

        // 2) Generate JWT
        return jwtService.generateToken(email);
    }


}


