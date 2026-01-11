error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java:java/util/Optional#isEmpty().
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
empty definition using pc, found symbol in pc: java/util/Optional#isEmpty().
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1412
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobApplicationController.java
text:
```scala
package VaLocaProject.Controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Models.User;
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
    public ResponseEntity<?> insertApplication(@RequestBody JobApplication jobApplication) {
        Optional<JobApplication> inserted = jobApplicationService.insertApplication(jobApplication);
        if (!inserted.i@@sEmpty()) {
            // application already existed
            return ResponseEntity.status(409).body(java.util.Map.of("error", "Application already exists"));
        }
        return ResponseEntity.ok(inserted.get());
    }

    @DeleteMapping("JobApplication/deleteAllApplications")
    public ResponseEntity<String> deleteAll(){
        jobApplicationService.deleteAllApplications();
        return ResponseEntity.ok("All job applications deleted");
    }

    @GetMapping("JobApplication/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByPostId(id));
    }

    // Parameters could be changed to 2 @PathVariable by "getApplicationsByIds/JobPost/{post_id}/User/{user_id}"
    @GetMapping("JobApplication/getApplicationByIds")
    public ResponseEntity<JobApplication> getApplicationByIds(@RequestParam Long post_id, @RequestParam Long user_id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationByIds(post_id, user_id));
    }
    

    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/Optional#isEmpty().