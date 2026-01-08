package VaLocaProject.Services;

import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Models.Company;
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

}
