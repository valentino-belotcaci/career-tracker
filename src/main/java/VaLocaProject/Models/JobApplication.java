package VaLocaProject.Models;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity // To define it as entity to springboot
@Table(name = "companies") // To define which table it represents
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    public Long applicationId;
    @Column(name = "post_id")
    public Long postId;
    @Column(name = "user_id")
    public Long userId;
    public Date created_at;


    public JobApplication(){}
}
