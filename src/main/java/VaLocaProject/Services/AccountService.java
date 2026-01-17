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
import VaLocaProject.Security.JWTService;

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

    public String authenticate(String email, String password) {

        Account account = getAccountByEmail(email);
        if (account == null || account.getPassword() == null) {
            return null;
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


    public void deleteAllAccounts(){
        accountRepository.deleteAll();
    }


}
