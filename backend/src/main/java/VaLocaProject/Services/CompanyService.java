package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Security.RedisService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RedisService redisService;

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

    public Company updateCompany(Long id, Company company) {
        return companyRepository.findById(id)
            .map(foundCompany -> {
                if (company.getEmail() != null) foundCompany.setEmail(company.getEmail());
                if (company.getName() != null) foundCompany.setName(company.getName());
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


    public Company getCompanyByAccountId(Long id) {
        String key = "company:" + id;

        // Try cache first, fallback to DB, save to cache if DB was used
        return Optional.ofNullable(redisService.get(key))
                // Before casting, check the type of the object
                .filter(Company.class::isInstance)
                // Cast the object to Company
                .map(Company.class::cast)
                // If cache missed, fetch from DB
                .or(() -> companyRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Company not found for id: " + id));
    }




    public Company getCompanyByEmail(String email){
        String key = "account:" + email;

        return Optional.ofNullable(redisService.get(key))
                // Before casting, check the type of the object
                .filter(Company.class::isInstance)
                .map(Company.class::cast)
                .or(() -> {
                    // If cache missed, fetch from DB
                    return companyRepository.findByEmail(email);
                })
                .orElseThrow(() -> new RuntimeException("Company not found for email: " + email));
    }

}




