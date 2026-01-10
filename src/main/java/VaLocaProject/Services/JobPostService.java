package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.JobPostRepository;

@Service
public class JobPostService {
    
    @Autowired
    JobPostRepository jobPostRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    public JobPost insertPost(JobPost jobPost){
        // Validate company exists
        if (jobPost.getCompanyId() == null || !companyRepository.existsById(jobPost.getCompanyId())) {
            throw new IllegalArgumentException("Company not found");
        }

        // Ensure createdAt is set (DB has NOT NULL constraint)
        if (jobPost.getCreatedAt() == null) {
            jobPost.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        }

        return jobPostRepository.save(jobPost);
    }

    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public JobPost updatePost(Long id, JobPost jobPost){
        JobPost foundJobPost = jobPostRepository.findById(id).orElseThrow(
            () -> new RuntimeException("JobPost not found"));
        // Check each field and update when non-null (or non-zero for primitives)
        if (jobPost.getCompanyId() != null) {
            foundJobPost.setCompanyId(jobPost.getCompanyId());
        }

        if (jobPost.getName() != null) {
            foundJobPost.setName(jobPost.getName());
        }

        if (jobPost.getDescription() != null) {
            foundJobPost.setDescription(jobPost.getDescription());
        }

        if (jobPost.getDuration() != null) {
            foundJobPost.setDuration(jobPost.getDuration());
        }

        if (jobPost.getAvailable() != null) {
            foundJobPost.setAvailable(jobPost.getAvailable());
        }

        // For salary (primitive int) only update when non-zero
        if (jobPost.getSalary() != 0) {
            foundJobPost.setSalary(jobPost.getSalary());
        }

        // Persist and return the updated entity
        return jobPostRepository.save(foundJobPost);
    }

    public List<JobPost> getPostByCompany(Long id){
        Company foundcompany = companyRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Company not found"));
        return jobPostRepository.findByCompanyId(foundcompany.company_id);
    }

    // New: get posts by company email. Returns empty list when company not found.
    public List<JobPost> getPostByCompanyEmail(String email){
        Company foundcompany = companyRepository.findByEmail(email);
        if (foundcompany == null) {
            return java.util.Collections.emptyList();
        }
        return jobPostRepository.findByCompanyId(foundcompany.company_id);
    }
}
