package VaLocaProject.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    @NativeQuery(value = "SELECT * FROM job_posts WHERE company_id = :companyId")
    List<JobPost> findByCompanyId(@Param("companyId") Long companyId);
}
