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
    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "street", nullable = true)
    private String street;

    @Column(name = "number", nullable = true)
    private String number;

    // No-arg constructor for JPA
    public Company() {
        super();
    }

    // Constructor that initializes company fields
    public Company(String email, String password) {
        super(email, password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
