error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java:_empty_/JobApplicationRepository#findBypost_idAnduser_id#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
empty definition using pc, found symbol in pc: _empty_/JobApplicationRepository#findBypost_idAnduser_id#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1099
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
        
        jobApplication.setCreated_at(new java.sql.Date(System.currentTimeMillis()));

        return jobApplicationRepository.save(jobApplication);
    }

    public void deleteAllApplications(){
        jobApplicationRepository.deleteAll();
    }

    public List<JobApplication> getApplicationsByPostId(Long postId){
        return jobApplicationRepository.findByPostId(postId);
    }

    public JobApplication getApplicationByIds(Long post_id, Long user_id){
        return jobApplicationRepository.findBypos@@t_idAnduser_id(post_id, user_id);
    }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JobApplicationRepository#findBypost_idAnduser_id#