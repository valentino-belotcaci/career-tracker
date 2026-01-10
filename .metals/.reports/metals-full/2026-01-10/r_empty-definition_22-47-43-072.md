error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java:VaLocaProject/Repositories/JobApplicationRepository#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
empty definition using pc, found symbol in pc: VaLocaProject/Repositories/JobApplicationRepository#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 246
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobApplicationService.java
text:
```scala
package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.@@JobApplicationRepository;

@Service
public class JobApplicationService{

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();
    }

    public JobApplication insertApplication(JobApplication jobApplication){
        return jobApplicationRepository.save(jobApplication);
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: VaLocaProject/Repositories/JobApplicationRepository#