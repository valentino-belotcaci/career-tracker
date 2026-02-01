package VaLocaProject.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.JobPostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    
    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }
         
    @Cacheable("AllJobPosts")
    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    @Caching(
    // Updates the cache for the specific job post, and evicts the company posts list cache 
    put = @CachePut(value = "jobposts", key = "#result.id"),
    evict = @CacheEvict(value = "jobpostsByCompany", key = "#jobPost.companyId"))
    public JobPost insertPost(JobPost jobPost) {
        if (jobPost.getCompanyId() == null) {
            throw new IllegalStateException("JobPost must have a companyId");
        }
        jobPost.setCreatedAt(LocalDateTime.now());

        return jobPostRepository.save(jobPost);
    }

   @Caching(evict = {
    // To delete the single post form caches and form the company list of posts
    // FIX: for now we invalidate all the company posts cache, we could pass the companyId too
    // as a second parameter and clear the cache only for that company
    @CacheEvict(value = "jobposts", key = "#id"),
    @CacheEvict(value = "jobpostsByCompany", allEntries = true)
    })
    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    // Invalidate chaches for all posts and all company posts
    @CacheEvict(value = {"jobposts", "jobpostsByCompany"}, allEntries = true)
    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }


    @Transactional
    // Now the updatePost method updates the cache for the specific job post
    // and invalidates the cache for the list of posts by the company
    @Caching(
    put = @CachePut(value = "jobposts", key = "#result.id"),
    evict = @CacheEvict(value = "jobpostsByCompany", key = "#jobPost.companyId"))       
    public JobPost updatePost(Long id, JobPost jobPost) {
        // 1) Fetch from DB (managed entity)
        JobPost presentJob = jobPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found with id " + id));

        // 2) Update fields if non-null
        if (jobPost.getCompanyId() != null) presentJob.setCompanyId(jobPost.getCompanyId());
        if (jobPost.getName() != null) presentJob.setName(jobPost.getName());
        if (jobPost.getDescription() != null) presentJob.setDescription(jobPost.getDescription());
        if (jobPost.getDuration() != null) presentJob.setDuration(jobPost.getDuration());
        if (jobPost.getAvailable() != null) presentJob.setAvailable(jobPost.getAvailable());
        if (jobPost.getSalary() != null) presentJob.setSalary(jobPost.getSalary());

        // Return the jpa managed entity (JPA will auto-persist changes)
        return presentJob;
    }

    @Cacheable("jobpostsByCompany")
    public List<JobPost> getPostsByCompanyId(Long companyId) {
        return jobPostRepository.findPostsByCompanyId(companyId);
    }

    @Cacheable("jobposts")
    public JobPost getPostByPostId(Long id) {

        // Fallback to DB
        return jobPostRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("JobPost not found with id " + id));
    }
}
