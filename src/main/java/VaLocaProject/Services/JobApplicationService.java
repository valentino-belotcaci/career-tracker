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

    public Optional<JobApplication> insertApplication(JobApplication jobApplication){
        JobApplication foundApplication = getApplicationByIds(jobApplication.getPostId(), jobApplication.getUserId());
        // If the user already submitted an application for this post, return empty to indicate no new creation
        if (foundApplication != null) {
            return Optional.empty();
        }

        // Create the timestamp of the job application
        jobApplication.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        JobApplication saved = jobApplicationRepository.save(jobApplication);
        return Optional.ofNullable(saved);
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

}