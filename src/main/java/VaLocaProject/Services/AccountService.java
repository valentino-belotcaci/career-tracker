package VaLocaProject.Services;

@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

}
