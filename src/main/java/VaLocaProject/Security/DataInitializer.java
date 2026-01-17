package VaLocaProject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import VaLocaProject.Models.Account;
import VaLocaProject.Services.AccountService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@1";
        if (accountService.getAccountByEmail(adminEmail) == null) {
            Account admin = new Account();
            admin.setEmail(adminEmail);
            admin.setPassword("adminPassword"); // will be hashed inside insertAccount
            admin.setType("ADMIN");
            accountService.insertAccount(admin);
            System.out.println("Created admin account: " + adminEmail);
        }
    }
}