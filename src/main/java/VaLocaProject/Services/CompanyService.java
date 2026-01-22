package VaLocaProject.Services;

import java.time.Duration;
import java.util.List;

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

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company insertCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }

    public Company updateCompany(Long id, Company company){
        // Return if else null to resolve Optional
        Company foundCompany = companyRepository.findById(id).orElse(null);
        if (foundCompany == null) {
            throw new RuntimeException("Company not found");
        }

        // Check if not null to not update some fields as null
        if (company.getEmail() != null) {
            foundCompany.setEmail(company.getEmail());
        }

        if (company.getName() != null) {
            foundCompany.setName(company.getName());
        }
        
        if (company.getDescription() != null) {
            foundCompany.setDescription(company.getDescription());
        }
        // Add other fields to update...

        // Actualy submit the new company version
        return companyRepository.save(foundCompany);
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

    public Company getCompanyByEmail(String email){
        String key = "company:" + email;

        try {
            Object cached = redisService.get(key);
            if (cached instanceof Company company)  return company;

        } catch (Exception e) {
        }
        Company company = companyRepository.findByEmail(email);

        if (company != null){
            try {
                redisService.save(key, company, COMPANY_CACHE_TTL);
            } catch (Exception e) {
            }
        }

        return company;
    };
}
