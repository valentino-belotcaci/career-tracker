package VaLocaProject.Controllers;

@RestController
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @GetMapping("/Account/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts(){

        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
