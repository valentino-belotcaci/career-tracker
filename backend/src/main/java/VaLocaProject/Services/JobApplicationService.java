package VaLocaProject.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
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


        return jobApplicationRepository.save(jobApplication);
    }

    @Cacheable("jobApplications")
    public JobApplication getApplicationById(UUID id) {
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
        @CacheEvict(value = "jobApplicationsByUser", allEntries = true),
        @CacheEvict(value = "jobApplicationsByIds", allEntries = true)
    })
    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    @Caching(evict = {
        @CacheEvict(value = "jobApplications", key = "#id"),
        @CacheEvict(value = "jobApplicationsByPost", allEntries = true),
        @CacheEvict(value = "jobApplicationsByUser", allEntries = true)
    })
    public void deleteApplication(UUID id){
        jobApplicationRepository.deleteById(id);
    }

    @Cacheable("jobApplicationsByPost")
    public List<JobApplication> getApplicationsByPostId(UUID postId){
        return jobApplicationRepository.findApplicationsByPostId(postId);
    }

    @Cacheable(value = "jobApplicationsByIds", key = "{#postId, #userId}")    
    public JobApplication getApplicationByIds(UUID postId, UUID userId) {
        // Fallback to DB
        JobApplication jobApplication = jobApplicationRepository
            .findByPostIdAndUserId(postId, userId)
            .orElseThrow(() -> new EntityNotFoundException(
                "JobApplication not found with postId " + postId + " and userId " + userId));

        return jobApplication;
    }

    @Cacheable("jobApplicationsByUser")
    public List<JobApplication> getApplicationsByUserId(UUID id){
        
        return jobApplicationRepository.findApplicationsByUserId(id);
    }


    @Transactional
    // Additional security check to ensure that only the owner of the application can update it
    @PreAuthorize("#jobApplication.userId.toString() == authentication.principal.id")
    // Now the updatePost method updates the cache for the specific job post
    // and invalidates the cache for the list of posts by the company
    @Caching(
    put = @CachePut(value = "jobApplications", key = "#result.applicationId"),
    evict = {@CacheEvict(value = "jobApplicationsByUser", key = "#jobApplication.userId"),
        @CacheEvict(value = "jobApplicationsByPost", key = "#jobApplication.postId"),
        @CacheEvict(value = "jobApplicationsByIds", key = "#result.postId.toString().concat('-').concat(#result.userId.toString())")    })      
    public JobApplication updateApplication(UUID id, JobApplication jobApplication) {
        // 1) Fetch from DB (managed entity)
        
        JobApplication presentApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found with id " + id));

        // 2) Update fields if non-null
        if (jobApplication.getPostId() != null) presentApplication.setPostId(jobApplication.getPostId());
        if (jobApplication.getUserId() != null) presentApplication.setUserId(jobApplication.getUserId());
        if (jobApplication.getStatus() != null) presentApplication.setStatus(jobApplication.getStatus());
        if (jobApplication.getUserDescription() != null) presentApplication.setUserDescription(jobApplication.getUserDescription());


        // Return the jpa managed entity (JPA will auto-persist changes)
        return presentApplication;
    }
}