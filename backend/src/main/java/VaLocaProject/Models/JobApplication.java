package VaLocaProject.Models;


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
    @Column(name = "user_description")
    private String userDescription;
    @Column(name = "created_at")
    private LocalDateTime createdAt;




    public Long getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


    public Long getUserId() {
        return userId;
    }

    public void getUsertId(Long id) {
        userId = id;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUsertDescription(String userDescription) {
        this.userDescription = userDescription;
    }
}
