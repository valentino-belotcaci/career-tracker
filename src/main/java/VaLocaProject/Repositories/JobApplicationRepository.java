package VaLocaProject.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.JobApplication;
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // Derived query to find all applications for a given post id
    List<JobApplication> findByPostId(Long postId);

    // Use camelCase so Spring Data can derive the query from entity property names
    JobApplication findByPostIdAndUserId(Long postId, Long userId);
    List<JobApplication> findByUserId(Long userId);
}
