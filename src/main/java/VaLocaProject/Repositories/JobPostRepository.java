package VaLocaProject.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    @Query(value = "SELECT * FROM job_posts WHERE company_id = :companyId", nativeQuery = true)
    List<JobPost> findByCompanyId(@Param("companyId") Long companyId);
}
