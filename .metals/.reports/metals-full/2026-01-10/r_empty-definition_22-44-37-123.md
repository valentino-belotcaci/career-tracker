error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Models/JobApplication.java:java/time/LocalDateTime#
file://<WORKSPACE>/src/main/java/VaLocaProject/Models/JobApplication.java
empty definition using pc, found symbol in pc: java/time/LocalDateTime#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 99
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Models/JobApplication.java
text:
```scala
package VaLocaProject.Models;


import java.sql.Date;
import java.time.LocalDate;
import java.time.@@LocalDateTime;

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
@Data
@Table(name = "job_application") // To define which table it represents
@NoArgsConstructor
@AllArgsConstructor // Creates specific constructors for all fields
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "user_id")
    private Long userId;
    private String status;
    private LocalDateTime created_at;


}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/time/LocalDateTime#