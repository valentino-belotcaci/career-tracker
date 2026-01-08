
package VaLocaProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // To define it as entity to springboot
@Table(name = "users") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
@NoArgsConstructor
@AllArgsConstructor // Creates specific constructors for all fields
public class User {
    @Id // To define the id column of the table
    // "GeneratedValue" tells JPA that that value is generated.
    // "strategy = GenerationType.AUTO" tells JPA 
    // to AUTO choose the best strategy itself(probably just number increasing e.g. 1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "user_id")
    private Long user_id; // To define actual SQL column
    private String name;
    private String email;
    private Long account_id;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
}