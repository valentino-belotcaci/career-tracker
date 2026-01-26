package VaLocaProject.Services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Security.RedisService;

@Service
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RedisService redisService;

    // encode passwords on update
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private static final Duration COMPANY_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company insertCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }

    public Optional<Company> updateCompany(Long id, Company company){
        // Use map to update present fields and return Optional<Company>
        return companyRepository.findById(id).map(foundCompany -> {
            if (company.getEmail() != null) foundCompany.setEmail(company.getEmail());
            if (company.getName() != null) foundCompany.setName(company.getName());
            if (company.getDescription() != null) foundCompany.setDescription(company.getDescription());
            if (company.getPassword() != null) foundCompany.setPassword(passwordEncoder.encode(company.getPassword()));
            if (company.getCity() != null) foundCompany.setCity(company.getCity());
            if (company.getStreet() != null) foundCompany.setStreet(company.getStreet());
            if (company.getNumber() != null) foundCompany.setNumber(company.getNumber());

            // Actually updates the company
            // .save JPA method never returns null so we can use this directly
            Company saved = companyRepository.save(foundCompany);
            // Updates the cache with the new data
            try {
                String companyId = "company:" + saved.getId();
                redisService.save(companyId, saved, COMPANY_CACHE_TTL);
                if (saved.getEmail() != null) {
                    String companyEmail = "company:" + saved.getEmail();
                    redisService.save(companyEmail, saved, COMPANY_CACHE_TTL);
                }
            } catch (Exception ignored) {}

            return saved;
        });
    }

    public Company getCompanyByAccountId(Long id) {
        String key = "company:" + id;

        // 1) Try Redis first
        try {
            Object cached = redisService.get(key);
            if (cached instanceof Company company) {
                return company;
            }
        } catch (Exception e) {
            System.err.println("Redis read error: " + e.getMessage());
        }

        // 2) Fallback to DB
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found for id: " + id));

        // 3) Cache the DB result (side-effect)
        try {
            redisService.save(key, company, COMPANY_CACHE_TTL);
        } catch (Exception e) {
            System.err.println("Redis save error: " + e.getMessage());
        }

        return company;
    }



    public Company getCompanyByEmail(String email){
        String key = "company:" + email;

        try {
            Object cached = redisService.get(key);
            if (cached instanceof Company company) return company;
            
        } catch (Exception e) {
        }
        

        // Call map on a Optional (call the method safely with ofNullable())
        Company company = companyRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Company not found: " + email));
       
        try {
            redisService.save(key, company, COMPANY_CACHE_TTL);
        } catch (Exception e) {
        }
        return company;

    }

}


