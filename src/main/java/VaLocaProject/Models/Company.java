package VaLocaProject.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // To define it as entity to springboot
@Table(name = "companies") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    public Long companyId;
    public String name;
    public String email;
    public String description;
    @Column(name = "account_id")
    public Long accountId;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Long getCompanyId(){
        return companyId;
    }



    // Explicit all-args constructor to avoid relying on Lombok during compilation
    public Company(Long companyId, String name, String email, String description, Long accountId) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.description = description;
        this.accountId = accountId;
    }

    // Explicit no-args constructor
    public Company() {
    }

}
