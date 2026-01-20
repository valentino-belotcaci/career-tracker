package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VaLocaProject.Models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByEmail(String email);

}
