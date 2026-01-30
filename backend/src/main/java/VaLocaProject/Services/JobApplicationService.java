package VaLocaProject.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.JobApplicationRepository;
import VaLocaProject.Security.RedisService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class JobApplicationService{

    private final JobApplicationRepository jobApplicationRepository;

    private final RedisService redisService;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            RedisService redisService
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.redisService = redisService;
    }

    private static final Duration APPLICATION_CACHE_TTL = Duration.ofHours(1); // Defines lifetime of cache


    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();
    }

    public JobApplication insertApplication(JobApplication jobApplication) {
        if (jobApplication.getPostId() == null || jobApplication.getUserId() == null) {
            throw new IllegalStateException("JobApplication must have a postId and userId");
        }
        jobApplication.setCreatedAt(LocalDateTime.now());

        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication getApplicationById(Long id) {
        String key = "jobApplication:" + id;

        // 1) Try cache
        Object cached = redisService.get(key);
        if (cached instanceof JobApplication jobApplication) {
            return jobApplication;
        }

        // 2) Fallback to DB
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("JobApplication not found with id " + id));

        // 3) Save to cache
        try {
            redisService.save(key, application, APPLICATION_CACHE_TTL);
        } catch (Exception ignored) {}

        return application;
    }


    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findApplicationsByPostId(postId);
    }

    public JobApplication getApplicationByIds(Long postId, Long userId) {
        String key = "jobApplication:postId:" + postId + "userId:" + userId;

        // 1) Try cache
        Object cached = redisService.get(key);
        if (cached instanceof JobApplication jobApplication) {
            return jobApplication;
        }

        // 2) Fallback to DB
        JobApplication jobApplication = jobApplicationRepository
                .findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "JobApplication not found with postId " + postId + " and userId " + userId
                ));

        // 3) Save to cache
        try {
            redisService.save(key, jobApplication, APPLICATION_CACHE_TTL);
        } catch (Exception ignored) {}

        return jobApplication;
    }



    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findApplicationsByUserId(id);
    }
}