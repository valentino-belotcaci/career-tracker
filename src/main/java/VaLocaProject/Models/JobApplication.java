package VaLocaProject.Models;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "job_applications") // To define which table it represents
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
    private Date createdAt;


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date date) {
        createdAt = date;
    }

    public Long getPostId() {
        return postId;
    }

    public void getPostId(Long id) {
        postId = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void getUsertId(Long id) {
        userId = id;
    }
}
