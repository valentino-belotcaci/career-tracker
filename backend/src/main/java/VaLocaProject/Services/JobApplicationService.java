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

        Optional<JobApplication> existingApplication = Optional.ofNullable(getApplicationByIds(
                jobApplication.getPostId(),
                jobApplication.getUserId()
        ));

        if (existingApplication.isPresent()) {
            throw new IllegalStateException(
                    "User has already applied to this job post"
            );
        }

        // If application not already present, create the date and save it
        jobApplication.setCreatedAt(LocalDateTime.now());
        return jobApplicationRepository.save(jobApplication);
    }


    public JobApplication getApplicationById(Long id) {
        String key = "jobApplication:" + id;

        // 1) Try Redis cache
        Object cached = redisService.get(key);
        if (cached instanceof JobApplication cachedApplication) {
            return cachedApplication;
        }

        // 2) Fetch from DB using Optional internally
        Optional<JobApplication> optionalApplication =
                jobApplicationRepository.findById(id);

        JobApplication jobApplication = optionalApplication.orElseThrow(
                () -> new EntityNotFoundException(
                        "JobApplication not found with id " + id
                )
        );

        // 3) Cache result
        redisService.save(key, jobApplication, APPLICATION_CACHE_TTL);

        return jobApplication;
    }


    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }

    public JobApplication getApplicationByIds(Long post_id, Long user_id){
        return jobApplicationRepository.findByPostIdAndUserId(post_id, user_id);
    }

    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findByUserId(id);
    }
}