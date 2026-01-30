package VaLocaProject.Models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // To define it as entity to springboot
@Table(name = "companies") // To define which table it represents
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class Company extends Account {

    // Use nullable columns and avoid default empty strings so DB receives NULL when value is not provided
    @Column(name = "company_name", nullable = true)
    private String CompanyName;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "street", nullable = true)
    private String street;

    @Column(name = "number", nullable = true)
    private String number;

    // No-arg constructor for JPA
    protected Company() {
    }
    public Company(Long id) {
        super(id);
    }
    // Constructor that initializes company fields
    public Company(String email, String password) {
        super(email, password);
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getStreet(){
        return street;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

}
