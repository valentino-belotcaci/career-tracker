package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.Company;


public interface CompanyRepository extends JpaRepository<Company, Long>{
    Company findByEmail(String email);
}
