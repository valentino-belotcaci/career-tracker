package VaLocaProject.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    List<JobPost> findPostsByCompanyId(UUID companyId);
}
