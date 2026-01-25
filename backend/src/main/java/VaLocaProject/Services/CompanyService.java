package VaLocaProject.Services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Duration COMPANY_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache

    public Optional<List<Company>> getAllCompanies(){
        return Optional.of(companyRepository.findAll());
    }

    public Optional<Company> insertCompany(Company company){
        return Optional.of(companyRepository.save(company));
    }

    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }

    public Optional<Company> updateCompany(Long id, Company company){
        // Use map to update present fields and return Optional<Company>
        return companyRepository.findById(id).map(existing -> {
            if (company.getEmail() != null) existing.setEmail(company.getEmail());
            if (company.getName() != null) existing.setName(company.getName());
            if (company.getDescription() != null) existing.setDescription(company.getDescription());

            // Actually updates the company
            Company saved = companyRepository.save(existing);
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

    public Company getCompanyByAccountId(Long id){
        String key = "company:" + id;

        // try read from redis first
        try {
            Object cached = redisService.get(key);
            if (cached instanceof Company company)  return company;
            
        } catch (Exception ignored) {}

        // fallback to DB
        Company company = companyRepository.findById(id).orElse(null);

        // cache the DB result if found
        if (company != null) {
            try {
                redisService.save(key, company, COMPANY_CACHE_TTL);
            } catch (Exception ignored) {}
        }

        return company;
    }

    public Optional<Company> getCompanyByEmail(String email){
        String key = "company:" + email;

        try {
            Object cached = redisService.get(key);
            if (cached instanceof Company company) return Optional.of(company);

        } catch (Exception e) {
        }

        Optional<Company> company = Optional.ofNullable(companyRepository.findByEmail(email));

        if (company.isPresent()){
            try {
                redisService.save(key, company, COMPANY_CACHE_TTL);
            } catch (Exception e) {
            }
        }

        return company;
    };
}

