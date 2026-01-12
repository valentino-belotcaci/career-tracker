error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/CompanyController.java:VaLocaProject/Models/User#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/CompanyController.java
empty definition using pc, found symbol in pc: VaLocaProject/Models/User#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 347
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/CompanyController.java
text:
```scala
package VaLocaProject.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Services.CompanyService;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.@@User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CompanyController {
    
    @Autowired
    CompanyService companyService;

    // Returns all company
    @GetMapping("Company/getAllCompanies")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    // Inserts a new company
    @PostMapping("Company/insertCompany")
    public ResponseEntity<Company> insertCompany(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.insertCompany(company));
    }


     @DeleteMapping("Company/deleteAllCompanies")
    public ResponseEntity<String> deleteAllCompanies(){
        companyService.deleteAllCompanies();
        return ResponseEntity.ok("All companies deleted");
    }
    
    // Updates a company's fields
    @PutMapping("Company/updateCompany/{id}")
    public ResponseEntity<Company> updateUser(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(companyService.updateCompany(id, company));
    }

    @GetMapping("Company/getCompanyByAccountId/{id}")
    public ResponseEntity<Company> getCompanyByAccountId(@PathVariable Long id) {
        Company company = companyService.getCompanyByAccountId(id);

        // Additional check
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }



}

```


#### Short summary: 

empty definition using pc, found symbol in pc: VaLocaProject/Models/User#