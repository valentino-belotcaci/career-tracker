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
import jakarta.persistence.EntityNotFoundException;

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
        // 1) Ensure applicationId is set
        if (jobApplication.getApplicationId() == null) {
            throw new IllegalStateException("JobApplication must have an applicationId");
        }

        // 2) Check if an application with this ID already exists
        Optional.ofNullable(jobApplication.getApplicationId())
                .flatMap(jobApplicationRepository::findById)
                .ifPresent(existing -> {
                    throw new IllegalStateException(
                            "JobApplication already exists with id " + jobApplication.getApplicationId()
                    );
                });

        // 3) Save new application
        jobApplication.setCreatedAt(LocalDateTime.now());
        return jobApplicationRepository.save(jobApplication);
    }


    public JobApplication getApplicationById(Long id) {
        String key = "jobApplication:" + id;

        return Optional.ofNullable(redisService.get(key))      // Try cache first
                .filter(JobApplication.class::isInstance)      // Ensure it's the right type
                .map(JobApplication.class::cast)
                .or(() -> jobApplicationRepository.findById(id) // Fallback to DB
                        .map(jobApplication -> {
                            try {
                                redisService.save(key, jobApplication, APPLICATION_CACHE_TTL);
                            } catch (Exception ignored) {} // Ignore cache failures
                            return jobApplication;
                        }))
                .orElseThrow(() -> new EntityNotFoundException(
                        "JobApplication not found with id " + id
                ));
    }



    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }

    public JobApplication getApplicationByIds(Long postId, Long userId) {
        String key = "jobApplication:postId:" + postId + ":userId:" + userId;

        // 1) Try cache first
        return Optional.ofNullable(redisService.get(key))
            .filter(JobApplication.class::isInstance)
            .map(JobApplication.class::cast)
            .orElseGet(() -> {
                // 2) Fallback to DB
                JobApplication jobApplication = jobApplicationRepository
                    .findByPostIdAndUserId(postId, userId)
                        .orElseThrow(() -> new EntityNotFoundException(
                            "JobApplication not found with postId " + postId + " and userId " + userId
                        ));

                // 3) Save to cache (side-effect separated from transformations)
                try {
                    redisService.save(key, jobApplication, APPLICATION_CACHE_TTL);
                } catch (Exception ignored) {}

                return jobApplication;
                });
    }


    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findByUserId(id);
    }
}