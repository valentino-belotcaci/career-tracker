package VaLocaProject.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.JobPostRepository;
import VaLocaProject.Security.RedisService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class JobPostService {
    
    
    private final JobPostRepository jobPostRepository;
    
    private final RedisService redisService;

    public JobPostService(
            JobPostRepository jobPostRepository,
            RedisService redisService
    ) {
        this.jobPostRepository = jobPostRepository;
        this.redisService = redisService;
    }
    
    private static final Duration POST_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache
     

    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    public JobPost insertPost(JobPost jobPost) {
        if (jobPost.getCompanyId() == null) {
            throw new IllegalStateException("JobPost must have a companyId");
        }
        jobPost.setCreatedAt(LocalDateTime.now());

        return jobPostRepository.save(jobPost);
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    @Transactional
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

        // 3) update cache
        try {
            redisService.save("jobPost:" + id, presentJob, POST_CACHE_TTL);
        } catch (Exception ignored) {}

        // 4) Return the jpa managed entity (JPA will auto-persist changes)
        return presentJob;
    }


    public List<JobPost> getPostsByCompanyId(Long companyId) {
        return jobPostRepository.findPostsByCompanyId(companyId);
    }

    public JobPost getPostByPostId(Long id) {
        String key = "jobPost:" + id;

        // Try cache first 
        Object cached = redisService.get(key);
        if (cached instanceof JobPost jobPost) {
            return jobPost;
        }
        
        // Fallback to DB
        JobPost post = jobPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found with id " + id));

        // Save to cache
        try {
            redisService.save(key, post, POST_CACHE_TTL);
        } catch (Exception ignored) {}
    
        return post;
    }


}
