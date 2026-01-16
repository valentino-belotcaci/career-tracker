package VaLocaProject.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // To define it as entity to springboot
@Table(name = "accounts") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
@NoArgsConstructor
@AllArgsConstructor // Creates specific constructors for all fields
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    public Long accountId;
    public String email;
    public String password;
    public String type;


    public Long getAccountId(){
        return accountId;
    }

    public void setAccountId(Long id){
        this.accountId = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
