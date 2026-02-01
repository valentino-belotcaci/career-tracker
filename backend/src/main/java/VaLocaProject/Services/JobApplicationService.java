package VaLocaProject.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.JobApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class JobApplicationService{

    private final JobApplicationRepository jobApplicationRepository;


    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    //@Cacheable("AllJobApplications")
    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();
    }

    // Create cache annotation, update the jobApplication in the cache list 
    // then evict the list of applications for that post and user
    @Caching(
        put = @CachePut(value = "jobApplications", key = "#result.applicationId"),
        evict = {
            @CacheEvict(value = "jobApplicationsByPost", key = "#jobApplication.postId"),
            @CacheEvict(value = "jobApplicationsByUser", key = "#jobApplication.userId")
        }
    )
    @Transactional
    public JobApplication insertApplication(JobApplication jobApplication) {
        if (jobApplication.getPostId() == null || jobApplication.getUserId() == null) {
            throw new IllegalStateException("JobApplication must have a postId and userId");
        }
        jobApplication.setStatus("SUBMITTED");
        jobApplication.setCreatedAt(LocalDateTime.now());

        return jobApplicationRepository.save(jobApplication);
    }

    @Cacheable("jobApplications")
    public JobApplication getApplicationById(Long id) {
        // Fallback to DB
        JobApplication application = jobApplicationRepository.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException("JobApplication not found with id " + id));

        return application;
    }
    
    // Remove cache form list of jobApplications, and for the lists by post and user
    @Caching(evict = {
        @CacheEvict(value = "jobApplications", allEntries = true),
        @CacheEvict(value = "jobApplicationsByPost", allEntries = true),
        @CacheEvict(value = "jobApplicationsByUser", allEntries = true)
    })
    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    @Caching(evict = {
        @CacheEvict(value = "jobApplications", key = "#id"),
        @CacheEvict(value = "jobApplicationsByPost", allEntries = true),
        @CacheEvict(value = "jobApplicationsByUser", allEntries = true)
    })
    public void deleteApplication(Long id){
        jobApplicationRepository.deleteById(id);
    }

    @Cacheable("jobApplicationsByPost")
    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findApplicationsByPostId(postId);
    }

    @Cacheable(value = "jobApplicationsByIds", key = "#postId + '-' + #userId")
    public JobApplication getApplicationByIds(Long postId, Long userId) {
        // Fallback to DB
        JobApplication jobApplication = jobApplicationRepository
            .findByPostIdAndUserId(postId, userId)
            .orElseThrow(() -> new EntityNotFoundException(
                "JobApplication not found with postId " + postId + " and userId " + userId));

        return jobApplication;
    }

    @Cacheable("jobApplicationsByUser")
    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findApplicationsByUserId(id);
    }
}