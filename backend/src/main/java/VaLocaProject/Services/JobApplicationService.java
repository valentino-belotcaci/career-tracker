package VaLocaProject.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.JobApplicationRepository;
import VaLocaProject.Security.RedisService;

@Service
public class JobApplicationService{

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    @Autowired
    RedisService redisService;

    private static final Duration APPLICATION_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache


    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();
    }

    public JobApplication insertApplication(JobApplication jobApplication) {
        // Check if an application already exists
        Optional<JobApplication> foundApplication = getApplicationByIds(
                jobApplication.getPostId(), jobApplication.getUserId()
        );

        // If the user already submitted an application for this post, return empty
        if (foundApplication.isPresent()) {
            return null;
        }

        // Set the creation timestamp
        jobApplication.setCreatedAt(LocalDateTime.now());

        // Save the new application
        JobApplication saved = jobApplicationRepository.save(jobApplication);

        return saved;
    }

    public Optional<JobApplication> getApplicationById(Long id) {
        String key = "jobApplication:" + id;

        // 1) Try to get from Redis cache 
        Object cached = redisService.get(key);
        if (cached instanceof JobApplication cachedApplication) {
            // Found in cache, return immediately
            return Optional.of(cachedApplication);
        }

        // 2) Not in cache, fetch from DB and cache it
        return jobApplicationRepository.findById(id)
                .map(jobApplication -> {
                    // Save to cache for next time
                    redisService.save(key, jobApplication, APPLICATION_CACHE_TTL);
                    return jobApplication;
                });
    }

    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }

    public Optional<JobApplication> getApplicationByIds(Long post_id, Long user_id){
        Optional<JobApplication> jobApplication = Optional.of(jobApplicationRepository.findByPostIdAndUserId(post_id, user_id));
        if (jobApplication.isEmpty()) return Optional.empty();
        return jobApplication;
    }

    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findByUserId(id);
    }
}