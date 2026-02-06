package VaLocaProject.Models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Account {
    // id mapping is inherited by subclasses (User, Company) but Account is not an entity/table itself
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id") // align JPA mapping with your DB column
    private UUID id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "description", nullable = true)
    private String description = "";

    // No-arg constructor for JPA
    protected Account() {}

    // Constructor for subclasses to call. Don't accept id when it's auto-generated.
    protected Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // public constructor for tests
    public Account(UUID id) {
        this.id = id;
    }


    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    // returns "USER" if this instance is a User, "COMPANY" if Company, otherwise "UNKNOWN"
    @JsonProperty("type")
    public abstract String getType();
    
}