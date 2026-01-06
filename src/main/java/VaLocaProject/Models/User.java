
package VaLocaProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // To define it as entity to springboot
@Table(name = "user") // To define which table it represents
@Data // Generates getters, setters, toString(), equals(), hashCode()
@NoArgsConstructor
@AllArgsConstructor // Creates specific constructors for all fields
public class User {
    @Id // To define the id column of the table
    // "GeneratedValue" tells JPA that that value is generated.
    // "strategy = GenerationType.AUTO" tells JPA 
    // to AUTO choose the best strategy itself(probably just number increasing e.g. 1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId; // To define actual SQL column

    private String name;
        
    private int age;

}