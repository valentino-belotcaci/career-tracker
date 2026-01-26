package VaLocaProject.Controllers;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
       return Optional.ofNullable(accountService.getAllAccounts())
       .map((ResponseEntity::ok))
       .orElse(ResponseEntity.notFound().build());
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
        return ResponseEntity.ok(accountService.getAccountByEmail(email));
    }

    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAccount(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Error: email and password required");
        }

        
        String token = accountService.authenticate(email, password);
        if (token == null) {
            return ResponseEntity.status(401).body("Error: Login failed");
        }

        // Get account directly
        Account account = accountService.getAccountByEmail(email);

        // JWT cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();

        // Return JSON body with concrete type and id
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "type", account.getType(),
                        "id", account.getId()
                ));
    }

    // To remove all headers abd cookies when loggin out
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)   // keep consistent with authenticate cookie settings
                .path("/")
                .maxAge(0)      // expires immediately
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Logged out"));
    }

    /* AccountService updateAccount method needs to be fixed
    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }
         */


}

