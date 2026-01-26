package VaLocaProject.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.Company;
import VaLocaProject.Services.CompanyService;


@RestController
@RequestMapping("/Company")
public class CompanyController {
    
    @Autowired
    CompanyService companyService;

    // Returns all company
    @GetMapping("/getAllCompanies")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    // Inserts a new company
    @PostMapping("/insertCompany")
    public ResponseEntity<Company> insertCompany(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.insertCompany(company));
    }


     @DeleteMapping("/deleteAllCompanies")
    public ResponseEntity<String> deleteAllCompanies(){
        companyService.deleteAllCompanies();
        return ResponseEntity.ok("All companies deleted");
    }
    
    // Updates a company's fields
    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(companyService.updateCompany(id, company));
    }

    @GetMapping("/getCompanyByAccountId/{id}")
    public ResponseEntity<Company> getCompanyByAccountId(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyByAccountId(id));
    }

}
