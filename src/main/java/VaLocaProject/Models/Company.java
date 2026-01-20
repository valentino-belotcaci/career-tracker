package VaLocaProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // To define it as entity to springboot
@Table(name = "companies") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
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

}
