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
    CompanyService companyService;

    @Autowired
    UserService userService;

    // Injection of password encoder
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    public Optional<List<Account>> getAllAccounts(){
        // Creates a empty List in a Optional
        Optional<List<Account>> accounts = Optional.of(new ArrayList<>());

        // Tries to add the users, if none found, creates a new empty list
        accounts.get().addAll(userService.getAllUsers().orElseGet(ArrayList::new));
        accounts.get().addAll(companyService.getAllCompanies().orElseGet(ArrayList::new));
        return accounts;
    }

    // Return concrete lists when callers need subclass types
    public Optional<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    public Optional<List<Company>> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    public void deleteAllAccounts(){
        userService.deleteAllUsers();
        companyService.deleteAllCompanies();
    }

    // FIX: here maybe we should use Flatmap monadic method instead of map
    public Optional<Account> getAccountByEmail(String email){
        // create a optional of the return value of the repo function(either null or user)
        return userService.getUserByEmail(email)
                // Checks if it exists, it casts it to account
                .map(u -> (Account) u)
                // Or: if value present return that, if not return a new optional from the given supplier
                .or(() -> companyService.getCompanyByEmail(email))
                // if instead company present, cast it and return it
                .map(c -> (Account) c);

        /* More intuitive beginner version
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return Optional.of(user);
        }

        Company company = companyService.getCompanyByEmail(email);
        if (company != null) {
            return Optional.of(company);
        }

        return Optional.empty();
        */
    }

    // Logic for insertion of User and Company instances to the db
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




    public Optional<String> authenticate(String email, String password) {

        Optional<Account> account_found = getAccountByEmail(email);

        if (account_found.isEmpty()) return Optional.empty();
            
        // 1) Authenticate with RAW password
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        if (!authentication.isAuthenticated()) {
            return null;
        }

        // 2) Generate JWT
        Optional<String> token = Optional.of(jwtService.generateToken(email));
        
        if (token.isPresent()) return token;

        return Optional.empty();
    }



}


