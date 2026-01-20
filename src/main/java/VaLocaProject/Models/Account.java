package VaLocaProject.Models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Account {
    // id mapping is inherited by subclasses (User, Company) but Account is not an entity/table itself
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // align JPA mapping with your DB column
    private Long id;
    private String email;
    private String password;
    private String description = "";

    // No-arg constructor for JPA
    protected Account() {}

    // Constructor for subclasses to call. Don't accept id when it's auto-generated.
    protected Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
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

    
}
