error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java:java/util/List#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
empty definition using pc, found symbol in pc: java/util/List#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 655
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
text:
```scala
package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Li@@st<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account insertAccount(Account account){
       account.getAccountId() = null; // set as null the id to be set then by DB
        Account saved = accountRepository.save(account);

        // After creating the Account, also create a blank Company or User linked via account_id
        if (saved != null) {
            String type = saved.type;
            if (type.equals("COMPANY")) {
                // Company constructor order: company_id, name, email, description, account_id
                Company c = new Company(null, "", saved.email, "", saved.account_id);
                companyRepository.save(c);
            } else if (type.equals("USER")) {
                // User constructor order: user_id, name, email, description, account_id
                User u = new User(null, "", saved.email, "", saved.account_id);
                userRepository.save(u);
            }
        }

        return saved;
    }

    // Small helper to expose account 
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Boolean authenticate(String email, String password){
        // Search account instance based on the email
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            return false;
        }
        
        // Check if the password matches 
        // TODO: add password hashing 
        if (account.password == null) return false;
        return account.password.equals(password);
    }

    public void deleteAllAccounts(){
        accountRepository.deleteAll();
    }


}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/List#