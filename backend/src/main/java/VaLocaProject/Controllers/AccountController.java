package VaLocaProject.Controllers;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.DTO.UpdateAccountDTO;
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
        String jwtToken = accountService.insertAccount(email, password, type);

        Account saved = accountService.getAccountByEmail(email);

        ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
            .httpOnly(true)
            .secure(true) // Assicurati che React sia su HTTPS o usa false per localhost
            .path("/")
            .maxAge(Duration.ofMinutes(30))
            .sameSite("Lax")
            .build();

        if (saved == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(saved);
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
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // NOTE: This logic needs to stay here as it is Response handling logic,
    // so the service shouldn't interact with it
    @PostMapping("/authenticate")
    public ResponseEntity<Account> authenticateAccount(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // 1) Authenticate to the service and receive the JWT token as string
        String jwtToken = accountService.authenticate(email, password);

        // 2) DEfine the ResponseCookie with the JWT token.
        // The token needs to have the same configurations as the one defined in the SecurityConfig
        ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
            .httpOnly(true)
            .secure(true) // Assicurati che React sia su HTTPS o usa false per localhost
            .path("/")
            .maxAge(Duration.ofMinutes(30))
            .sameSite("Lax")
            .build();

        // 3) Fetch the account details to add in the body of the response
        Account account = accountService.getAccountByEmail(email);

        // Return the actual response with cookie inside the
        // the header and the logged account as the body
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(account);
    }

    // To remove all headers abd cookies when loggin out
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("token", "")
            .httpOnly(true)
            .secure(true)   // keep consistent with authenticate cookie settings
            .path("/")
            .maxAge(0)      // expires immediately
            .sameSite("Lax")
            .build();

        ResponseCookie csrfCookie = ResponseCookie.from("XSRF-TOKEN", "")
            .httpOnly(true) 
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, csrfCookie.toString())
            .body(Map.of("message", "Logged out"));
    }


    @PatchMapping("/updateAccount/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO update) {
        return ResponseEntity.ok(accountService.updateAccount(id, update));
    }


}