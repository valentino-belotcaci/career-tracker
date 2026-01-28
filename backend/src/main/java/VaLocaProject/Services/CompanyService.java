package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Company;
import VaLocaProject.Repositories.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

    // encode passwords on update
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company insertCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }

    public Company updateCompany(Long id, UpdateAccountDTO company) {
        return companyRepository.findById(id)
            .map(foundCompany -> {
                if (company.getEmail() != null) foundCompany.setEmail(company.getEmail());
                if (company.getCompanyName() != null) foundCompany.setName(company.getCompanyName());
                if (company.getDescription() != null) foundCompany.setDescription(company.getDescription());
                if (company.getPassword() != null)
                    foundCompany.setPassword(passwordEncoder.encode(company.getPassword()));
                if (company.getCity() != null) foundCompany.setCity(company.getCity());
                if (company.getStreet() != null) foundCompany.setStreet(company.getStreet());
                if (company.getNumber() != null) foundCompany.setNumber(company.getNumber());

                Company saved = companyRepository.save(foundCompany);

                return saved;
            })
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + id));
    }


    
    public Optional<Company> getCompanyByAccountId(Long id) {
        // Try cache first, fallback to DB, save to cache if DB was used
        return companyRepository.findById(id);
    }




    public Optional<Company> getCompanyByEmail(String email){
        return companyRepository.findByEmail(email);
                
    }

}




