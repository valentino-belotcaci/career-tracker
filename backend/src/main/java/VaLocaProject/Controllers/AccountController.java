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
       return accountService.getAllAccounts()
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
        return accountService.getAccountByEmail(email)
                // map checks if the value actually exists, performs some operations and return the monad like before
                .map(ResponseEntity::ok)
                // orElseGet: if the value in the monad is not present, return the value from the supplier function defined
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAccount(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Error: with email and password required");
        }

        Optional<String> token = accountService.authenticate(email, password);
        if (token == null) {
            return ResponseEntity.status(401).body(" Error:Login failed");
        }

        Optional<Account> accountOpt = accountService.getAccountByEmail(email);
        // extract type and id safely from Optional
        // map: takes a function with its type, returns a OPTIONAL result of the object present
        // so we add .orElse to define the other case (if not present)
        // Could be improved using bind()
        Optional<String> type = accountOpt.map(Account::getType);
        Optional<Long> id = accountOpt.map(Account::getId);

        /* Intuitive and less secure version of above
        if (accountOpt.isPresent()) {
            Account found = accountOpt.get();
            type = found.getType();
            id = found.getId();
        } */


        if (token.isEmpty()) return ResponseEntity.notFound().build();
        // set JWT in HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("token", token.get())
                .httpOnly(true)        // JS cannot read this cookie
                .secure(true)         // set true in production with HTTPS
                .path("/")             // cookie sent to all endpoints
                .maxAge(Duration.ofMinutes(15)) // token expiration
                .sameSite("Strict")    // CSRF protection
                .build();

        // Return cookie in header + type/id in JSON body
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("type", type, "id", id));
    }

    // FIX : Needs to be added in the frotnend for correct logout, we can add more things here
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


}

