error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java:VaLocaProject/Services/JobApplicationService#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
empty definition using pc, found symbol in pc: VaLocaProject/Services/JobApplicationService#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 309
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.@@JobApplicationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class JobApplicationController {
    
    @Autowired
    JobApplicationService jobApplicationService;

    @GetMapping("JobApplication/getAllApplications")
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllApplications());
    }

    @PostMapping("JobApplication/insertApplication")
    public ResponseEntity<JobApplication> insertApplication(@RequestBody JobApplication jobApplication) {
        return ResponseEntity.ok(jobApplicationService.insertApplication(jobApplication));
    }
    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: VaLocaProject/Services/JobApplicationService#