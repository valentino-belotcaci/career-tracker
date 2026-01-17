package VaLocaProject.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/Account")
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
    public ResponseEntity<?> authenticateAccount(@RequestBody Account account) {

        String token = accountService.authenticate(account.email, account.password);

        if (token == null) {
            return ResponseEntity.status(401).body("Login failed");
        }

        return ResponseEntity.ok(Map.of("token", token));
    }

}
