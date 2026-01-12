error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Models/Account.java:java/lang/String#
file://<WORKSPACE>/src/main/java/VaLocaProject/Models/Account.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1024
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Models/Account.java
text:
```scala
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


    public Long getAccountId(Account account){
        return account.accountId;
    }

    public void setAccountId(Long id){
        this.accountId = id;
    }

    public Long getEmail(@@String email){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#