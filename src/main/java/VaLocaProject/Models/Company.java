package VaLocaProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // To define it as entity to springboot
@Table(name = "companies") // To define which table it represents
public class Company extends Account{

    private String name = "";
    private String city = "";
    private String street = "";
    private String number = "";

    // No-arg constructor for JPA
    protected Company() {
        super();
    }

    // Constructor that initializes company fields
    public Company(String email, String password) {
        super(email, password);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
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
