package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.JobApplicationRepository;

@Service
public class JobApplicationService{

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();
    }

    public Optional<JobApplication> insertApplication(JobApplication jobApplication) {
        // Check if an application already exists
        Optional<JobApplication> foundApplication = getApplicationByIds(
                jobApplication.getPostId(), jobApplication.getUserId()
        );

        // If the user already submitted an application for this post, return empty
        if (foundApplication.isPresent()) {
            return Optional.empty();
        }

        // Set the creation timestamp
        jobApplication.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        // Save the new application
        JobApplication saved = jobApplicationRepository.save(jobApplication);

        return Optional.ofNullable(saved);
    }


    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }

    public Optional<JobApplication> getApplicationByIds(Long post_id, Long user_id){
        JobApplication jobApplication = jobApplicationRepository.findByPostIdAndUserId(post_id, user_id);
        if (jobApplication != null) return Optional.of(jobApplication);
        return Optional.empty();
    }

    public List<JobApplication> getApplicationsByUserId(Long id){
        return jobApplicationRepository.findByUserId(id);
    }
}