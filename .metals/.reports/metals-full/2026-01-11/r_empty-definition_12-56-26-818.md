error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/CompanyService.java:_empty_/Company#setEmail#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/CompanyService.java
empty definition using pc, found symbol in pc: _empty_/Company#setEmail#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1013
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/CompanyService.java
text:
```scala
package VaLocaProject.Services;

import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Company foundCompany = companyRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Company not found"));

        // Check if not null to not update some fields as null
        if (company.getEmail() != null) {
            foundCompany.setEma@@il(company.getEmail());
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
        return companyRepository.findByAccountId(id);
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Company#setEmail#