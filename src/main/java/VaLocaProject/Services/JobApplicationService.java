package VaLocaProject.Services;

import java.util.List;

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

    public JobApplication insertApplication(JobApplication jobApplication){
        
        jobApplication.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        return jobApplicationRepository.save(jobApplication);
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }
}