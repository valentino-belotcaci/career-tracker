package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	// Spring Data JPA derived query to find account by email
	Account findByEmail(String email);

}
