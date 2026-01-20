package VaLocaProject.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // To define it as entity to springboot
@Table(name = "users") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
public class User extends Account{

    @Column(name = "first_name")
    private String firstName = "";
    @Column(name = "last_name")
    private String lastName = "";

    // Explicit no-args constructor
    protected User() {
    }

    public User(String email, String password) {
		super(email, password);
	}
  
}