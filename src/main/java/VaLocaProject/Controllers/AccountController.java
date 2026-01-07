package VaLocaProject.Controllers;

import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.DTO.AccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Services.AccountService;

@RestController
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @GetMapping("/Account/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts(){

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping("Account/insertAccount")
    public ResponseEntity<AccountDTO> insertAccount(@RequestBody Account account){
        Account saved = accountService.insertAccount(account);

        AccountDTO accountDTO = new AccountDTO(
            saved.account_id,
            saved.email,
            saved.type
        );

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/Account/authenticate")
    public ResponseEntity<Boolean> authenticateAccount(@RequestBody Account account) {
        boolean isValid = accountService.authenticate(account.email, account.password);

        if (!isValid) {
            return ResponseEntity.status(401).body(false);
        }

        return ResponseEntity.ok(true);
    }

}
