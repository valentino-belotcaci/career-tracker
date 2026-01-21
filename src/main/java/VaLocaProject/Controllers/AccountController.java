package VaLocaProject.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
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
    public ResponseEntity<Account> insertAccount(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");
        String type = body.get("type");

        if (email == null || password == null || type == null) {
            return ResponseEntity.badRequest().build(); // return a ResponseEntity with no body with the header of badRequest
        }

        Account saved = accountService.insertAccount(email, password, type);
        if (saved == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/deleteAllAccounts")
    public ResponseEntity<String> deleteAllAccounts(){
        accountService.deleteAllAccounts();
        return ResponseEntity.ok("All accounts deleted");
    }

    @GetMapping("/getAccountByEmail/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        Optional<Account> opt = accountService.getAccountByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // If value is present, return the value
        return ResponseEntity.ok(opt.get());
    }
        
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAccount(@RequestBody Account account) {

        String token = accountService.authenticate(account.getEmail(), account.getPassword());

        if (token == null) {
            return ResponseEntity.status(401).body("Login failed");
        }

        String type;
        // determine account type so frontend can redirect to correct page
        if (account instanceof User) type = "USER";
        // BUG: CAN"T MATCH USER OR COMPANY
        else if (account instanceof Company) type = "COMPANY";
        else type = "UNKOWN";

        return ResponseEntity.ok(Map.of("token", token, "type", type));
    }

}
