package VaLocaProject.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.JobPostRepository;
import VaLocaProject.Security.RedisService;

@Service
public class JobPostService {
    
    @Autowired
    JobPostRepository jobPostRepository;

    @Autowired
    CompanyRepository companyRepository;

    
    @Autowired
    RedisService redisService;
    
    private static final Duration POST_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache
     

    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    public JobPost insertPost(JobPost jobPost){
        if (jobPost.getCompanyId() == null) {
            return null;
        }

        // Creates the current date to save
        jobPost.setCreatedAt(LocalDateTime.now());

        return jobPostRepository.save(jobPost);
    }

    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public Optional<JobPost> updatePost(Long id, JobPost jobPost) {
        String key = "jobPost:" + id;

        // 1) Try to get from cache 
        Object cachedObj = redisService.get(key);
        JobPost presentJob;

        if (cachedObj instanceof JobPost cachedJob) {
            presentJob = cachedJob; // use cached object
        } else {
            // Not in cache: fetch from DB
            Optional<JobPost> foundJobPost = jobPostRepository.findById(id);
            if (foundJobPost.isEmpty()) return Optional.empty();
            presentJob = foundJobPost.get();
        }

        //  2) Update fields if non-null
        if (jobPost.getCompanyId() != null) presentJob.setCompanyId(jobPost.getCompanyId());
        if (jobPost.getName() != null) presentJob.setName(jobPost.getName());
        if (jobPost.getDescription() != null) presentJob.setDescription(jobPost.getDescription());
        if (jobPost.getDuration() != null) presentJob.setDuration(jobPost.getDuration());
        if (jobPost.getAvailable() != null) presentJob.setAvailable(jobPost.getAvailable());
        if (jobPost.getSalary() != 0) presentJob.setSalary(jobPost.getSalary()); 

        //  3) Save updated object to DB
        JobPost updatedJobPost = jobPostRepository.save(presentJob);

        //  4) Save updated object to cache
        try {
            redisService.save(key, updatedJobPost, POST_CACHE_TTL);
        } catch (Exception ignored) {}

        return Optional.of(updatedJobPost);
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
