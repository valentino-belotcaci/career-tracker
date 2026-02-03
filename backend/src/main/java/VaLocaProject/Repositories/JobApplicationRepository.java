package VaLocaProject.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.JobApplication;
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    // Derived query to find all applications for a given post id
    List<JobApplication> findApplicationsByPostId(UUID postId);
    List<JobApplication> findApplicationsByUserId(UUID postId);


    // Use camelCase so Spring Data can derive the query from entity property names
    Optional<JobApplication> findByPostIdAndUserId(UUID postId, UUID userId);
}
