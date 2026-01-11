error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java:java/lang/System#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
empty definition using pc, found symbol in pc: java/lang/System#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 803
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
text:
```scala
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
        JobApplication foundApplication = getApplicationByIds(jobApplication.getPostId(), jobApplication.getUserId());
        
        if (!foundApplication) return 
        jobApplication.setCreatedAt(new java.sql.Date(Syst@@em.currentTimeMillis()));

        return jobApplicationRepository.save(jobApplication);
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/System#