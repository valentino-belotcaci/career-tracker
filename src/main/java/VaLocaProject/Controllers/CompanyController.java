package VaLocaProject.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CompanyController{

    @GetMapping("/Company")
    public String getString(){
        return "Company";
    }
    
}