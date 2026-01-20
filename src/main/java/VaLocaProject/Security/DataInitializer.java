package VaLocaProject.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import VaLocaProject.Services.AccountService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@1";
        Optional<?> existing = accountService.getAccountByEmail(adminEmail);
        if (existing.isEmpty()) {
            // create an admin
            accountService.insertAccount(adminEmail, "adminPassword", "ADMIN");
            System.out.println("Created admin account: " + adminEmail);
        }
    }
}