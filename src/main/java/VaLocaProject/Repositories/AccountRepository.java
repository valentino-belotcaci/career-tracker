package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Account getAccountByEmail(String email);
}
