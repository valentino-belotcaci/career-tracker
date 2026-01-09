package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.JobPostRepository;

@Service
public class JobPostService {
    
    @Autowired
    JobPostRepository jobPostRepository;

    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    public JobPost insertPost(JobPost jobPost){
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
}
