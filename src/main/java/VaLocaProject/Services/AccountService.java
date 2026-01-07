package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.AccountDTO;
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
        // Ensure we don't try to reuse a client-sent id — clear it so JPA will insert
        try { account.account_id = null; } catch (Exception ignored) {}
        return accountRepository.save(account);
    }

}
