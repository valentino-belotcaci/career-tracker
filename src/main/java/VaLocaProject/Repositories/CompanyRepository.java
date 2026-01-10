package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Use a native query so we can keep snake_case column/field names in the entity
    @Query(value = "SELECT * FROM companies WHERE account_id = :accountId", nativeQuery = true)
    Company findByAccountId(@Param("accountId") Long accountId);
}
