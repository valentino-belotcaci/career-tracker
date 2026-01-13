error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/AccountController.java:_empty_/RequestMapping#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/AccountController.java
empty definition using pc, found symbol in pc: _empty_/RequestMapping#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 682
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/AccountController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.DTO.AccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Services.AccountService;

@RestController
@Request@@Mapping("/Account")
public class AccountController {
    
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts(){

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping("/insertAccount")
    public ResponseEntity<AccountDTO> insertAccount(@RequestBody Account account){
        Account saved = accountService.insertAccount(account);

        AccountDTO accountDTO = new AccountDTO(
            saved.accountId,
            saved.email ,
            saved.type  
        );

        return ResponseEntity.ok(accountDTO);
    }


    @DeleteMapping("/deleteAllAccounts")
    public ResponseEntity<String> deleteAllAccounts(){
        accountService.deleteAllAccounts();
        return ResponseEntity.ok("All accounts deleted");
    }

    @GetMapping("/getAccountByEmail/{email}")
    public ResponseEntity<AccountDTO> getAccountByEmail(@PathVariable String email) {
        // return 404 if account not found
        Account account = accountService.getAccountByEmail(email);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        AccountDTO accountDTO = new AccountDTO(
            account.accountId,
            account.email,
            account.type
        );

        return ResponseEntity.ok(accountDTO);
    }
    

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateAccount(@RequestBody Account account) {
        boolean isValid = accountService.authenticate(account.email, account.password);

        if (!isValid) {
            return ResponseEntity.status(401).body(false);
        }

        return ResponseEntity.ok(true);
    }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/RequestMapping#