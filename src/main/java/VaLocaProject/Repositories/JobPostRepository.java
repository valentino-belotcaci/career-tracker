package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.JobPost;

public interface  JobPostRepository extends JpaRepository<JobPost, Long> {
    
}
