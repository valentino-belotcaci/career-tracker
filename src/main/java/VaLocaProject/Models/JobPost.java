package VaLocaProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "job_post") // To define which table it represents
@Data
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "post_id")
    private Long post_id;
    private Long company_id;
    private int salary;
    private String name;
    private String description;
    private String duration;
    private String available;

    // No-arg constructor required by JPA
    public JobPost() {
    }


    // Getters and setters
    public Long getPostId() {
        return post_id;
    }

    public void setPostId(Long post_id) {
        this.post_id = post_id;
    }

    public Long getCompanyId() {
        return company_id;
    }

    public void setCompanyId(Long company_id) {
        this.company_id = company_id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

}
