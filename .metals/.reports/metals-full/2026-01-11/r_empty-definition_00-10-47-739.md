error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java:_empty_/AccountRepository#findByEmail#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
empty definition using pc, found symbol in pc: _empty_/AccountRepository#findByEmail#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 955
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
text:
```scala
package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Account;
import VaLocaProject.Repositories.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account insertAccount(Account account){
       account.account_id = null; // set as null the id to be set then by DB
        return accountRepository.save(account);
    }

    // Small helper to expose account 
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Boolean authenticate(String email, String password){
        // Search account instance based on the email
        Account account = accountRepository.find@@ByEmail(email);

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

empty definition using pc, found symbol in pc: _empty_/AccountRepository#findByEmail#