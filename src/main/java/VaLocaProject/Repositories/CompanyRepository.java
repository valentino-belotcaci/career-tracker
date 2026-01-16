package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Use a native query so we can keep snake_case column/field names in the entity
    @NativeQuery(value = "SELECT * FROM companies WHERE account_id = :accountId")
    Company findByAccountId(@Param("accountId") Long accountId);
}
