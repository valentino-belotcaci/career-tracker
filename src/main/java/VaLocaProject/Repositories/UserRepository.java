package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.User;



public interface UserRepository extends JpaRepository<User, Long> {
        // Manually writing the SQL query to process, needed for some problem in the parameter naming to search for
        @Query(value = "SELECT * from users WHERE account_id = :accountId", nativeQuery=true)
        public User findByAccountId(@Param("accountId") Long id);
}