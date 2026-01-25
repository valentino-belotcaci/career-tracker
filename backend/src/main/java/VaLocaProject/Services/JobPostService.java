package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;

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
        if (jobPost.getCompanyId() == null) {
            throw new IllegalArgumentException("Company not found");
        }

        // Creates the current date to save (not fully working)
        jobPost.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        return jobPostRepository.save(jobPost);
    }

    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public Optional<JobPost> updatePost(Long id, JobPost jobPost){
        Optional<JobPost> foundJobPost = jobPostRepository.findById(id);

        if (foundJobPost.isEmpty()) return Optional.empty();
        
        // I think this part could be improved
        JobPost present_jb = foundJobPost.get();
        // Check each field and update when non-null (or non-zero for primitives)
        if (jobPost.getCompanyId() != null) {
            present_jb.setCompanyId(jobPost.getCompanyId());
        }

        if (jobPost.getName() != null) {
            present_jb.setName(jobPost.getName());
        }

        if (jobPost.getDescription() != null) {
            present_jb.setDescription(jobPost.getDescription());
        }

        if (jobPost.getDuration() != null) {
            present_jb.setDuration(jobPost.getDuration());
        }

        if (jobPost.getAvailable() != null) {
            present_jb.setAvailable(jobPost.getAvailable());
        }

        if (jobPost.getSalary() != 0) {
            present_jb.setSalary(jobPost.getSalary());
        }

        // Persist and return the updated entity
        return Optional.ofNullable(jobPostRepository.save(present_jb));
    }


    public List<JobPost> getPostsByCompanyId(Long id){
        // Checks if the company exists
        Company foundcompany = companyRepository.findById(id).orElse(null);
        
        return jobPostRepository.findByCompanyId(foundcompany.getId());
    }

    public Optional<JobPost> getPostByPostId(Long id) {
        return jobPostRepository.findById(id);
    }
}
