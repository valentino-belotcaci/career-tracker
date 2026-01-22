package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Repositories.CompanyRepository;

@Service
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

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
         // Return if else null to resolve Optional
        return companyRepository.findById(id).orElse(null);
    }

    public Company getCompanyByEmail(String email){
        return companyRepository.findByEmail(email);
    };
}
