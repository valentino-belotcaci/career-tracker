error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java:VaLocaProject/Models/User#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
empty definition using pc, found symbol in pc: VaLocaProject/Models/User#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 354
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Models.@@User;
import VaLocaProject.Services.JobApplicationService;

import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("JobApplication/deleteAll")
    public ResponseEntity<String> deleteAll(){
        jobApplicationService.deleteAll();
        return ResponseEntity.ok("All job applications deleted");
    }

    @GetMapping("JobApplication/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByPostId(id));
    }



    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: VaLocaProject/Models/User#