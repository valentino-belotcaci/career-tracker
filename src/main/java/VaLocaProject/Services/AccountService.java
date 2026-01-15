package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.AccountRepository;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;

@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;

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
        return accountRepository.findAll();
    }

    public Account insertAccount(Account account){
        // Hash the plaintext password before saving
        if (account.getPassword() != null) {
            String hashed = passwordEncoder.encode(account.getPassword());
            account.setPassword(hashed);
        }

        Account saved = accountRepository.save(account);



        // After creating the Account, also create a blank Company or User linked via account_id
        if (saved != null) {
            String type = saved.type;
            if (type.equals("COMPANY")) {
                // Company constructor order: company_id, name, email, description, account_id
                Company c = new Company(null, "", saved.getEmail(), "", saved.getAccountId());
                companyRepository.save(c);
            } else if (type.equals("USER")) {
                // User constructor order: user_id, name, email, description, account_id
                User u = new User(null, "", saved.getEmail(), "", saved.getAccountId());
                userRepository.save(u);
            }
        }

        return saved;
    }

    // Helper to get account 
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

public Boolean authenticate(String email, String password){
    // Search account instance based on the email
    Account account = getAccountByEmail(email);

    if (account == null) {
        return false;
    }

    // Check if the password exists on record
    if (account.getPassword() == null) return false;

    // Authenticate using the raw credentials (email + plain password)
    Authentication authentication;
    try {
        authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
    } catch (Exception ex) {
        return false;
    }

    // Check if the authentication actually succeeded
    if (!authentication.isAuthenticated()){
        return false;
    }

    // Generate JWT and return verification of the password against stored hash
    jwtService.generateToken(email);
    return passwordEncoder.matches(password, account.getPassword());
}

    public void deleteAllAccounts(){
        accountRepository.deleteAll();
    }


}
