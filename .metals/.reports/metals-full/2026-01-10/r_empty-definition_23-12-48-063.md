error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java:_empty_/JobApplicationService#getApplicationsByPostId#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
empty definition using pc, found symbol in pc: _empty_/JobApplicationService#getApplicationsByPostId#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1446
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



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

    @GetMapping("JobApplication/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable Long id) {
        return ReponseEntity.ok(jobApplicationService.get@@ApplicationsByPostId);
    }
    
    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JobApplicationService#getApplicationsByPostId#