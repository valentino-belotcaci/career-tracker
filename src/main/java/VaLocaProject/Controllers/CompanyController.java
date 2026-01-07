package VaLocaProject.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.CompanyService;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CompanyController {
    
    @Autowired
    CompanyService companyService;

    // Returns all company
    @GetMapping("/Company/getAllCompanies")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    // Inserts a new company
    @PostMapping("/Company/insertCompany")
    public ResponseEntity<Company> insertCompany(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.insertCompany(company));
    }
    
}
