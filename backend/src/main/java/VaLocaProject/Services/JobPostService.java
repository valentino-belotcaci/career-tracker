package VaLocaProject.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.JobPostRepository;
import VaLocaProject.Security.RedisService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class JobPostService {
    
    
    private final JobPostRepository jobPostRepository;
    
    private final CompanyRepository companyRepository;

    private final RedisService redisService;

    public JobPostService(
            JobPostRepository jobPostRepository,
            CompanyRepository companyRepository,
            RedisService redisService
    ) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
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



    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public JobPost updatePost(Long id, JobPost jobPost) {
        String key = "jobPost:" + id;

        JobPost presentJob;
        
        // 1) Try cache
        Object cachedObj = redisService.get(key);
        if (cachedObj instanceof JobPost cachedJob) {
            presentJob = cachedJob;
        } else {
            // 2) Fetch from DB using Optional internally
            presentJob = jobPostRepository.findById(id)
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "JobPost not found with id " + id
                            )
                    );
        }

        // 3) Update fields if non-null
        if (jobPost.getCompanyId() != null) presentJob.setCompanyId(jobPost.getCompanyId());
        if (jobPost.getName() != null) presentJob.setName(jobPost.getName());
        if (jobPost.getDescription() != null) presentJob.setDescription(jobPost.getDescription());
        if (jobPost.getDuration() != null) presentJob.setDuration(jobPost.getDuration());
        if (jobPost.getAvailable() != null) presentJob.setAvailable(jobPost.getAvailable());
        if (jobPost.getSalary() != 0) presentJob.setSalary(jobPost.getSalary());

        // 4) Save to DB
        JobPost updatedJobPost = jobPostRepository.save(presentJob);

        // 5) Update cache
        try {
            redisService.save(key, updatedJobPost, POST_CACHE_TTL);
        } catch (Exception ignored) {}

        return updatedJobPost;
    }


    public List<JobPost> getPostsByCompanyId(Long id){
        // Checks if the company exists
        return companyRepository.findById(id)
        .map(company -> {
            return jobPostRepository.findByCompanyId(company.getId());
        })
        .orElseThrow(() -> new EntityNotFoundException("Posts or Company not found"));
    }

    
    public JobPost getPostByPostId(Long id) {
        String key = "jobPost:" + id;

        return Optional.ofNullable(redisService.get(key))
                .filter(JobPost.class::isInstance)
                .map(JobPost.class::cast)
                .or(() -> jobPostRepository.findById(id)
                        .map(post -> {
                            try {
                                redisService.save(key, post, POST_CACHE_TTL);
                            } catch (Exception ignored) {}
                            return post;
                        }))
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found with id " + id));
    }

}
