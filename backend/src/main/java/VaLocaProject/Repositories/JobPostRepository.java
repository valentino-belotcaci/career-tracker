package VaLocaProject.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findPostsByCompanyId(Long companyId);
}
