error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java:_empty_/JobApplicationRepository#findAllById#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
empty definition using pc, found symbol in pc: _empty_/JobApplicationRepository#findAllById#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 852
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
        
        jobApplication.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        return jobApplicationRepository.save(jobApplication);
    }

    public List<JobApplication> getApplicationsByPostId(Long id){
        return jobApplicationRepository.findA@@llById(id);
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JobApplicationRepository#findAllById#