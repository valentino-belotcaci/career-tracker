package VaLocaProject.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/JobApplication")
public class JobApplicationController {
    
    @Autowired
    JobApplicationService jobApplicationService;

    @GetMapping("/getAllApplications")
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllApplications());
    }

    @PostMapping("/insertApplication")
    public ResponseEntity<?> insertApplication(@RequestBody JobApplication jobApplication) {
        Optional<JobApplication> inserted = jobApplicationService.insertApplication(jobApplication);
        // If the Optional is empty the service found an existing application and did not create a new one
        if (inserted.isEmpty()) {
            return ResponseEntity.status(409).body(Map.of("error", "Application already exists"));
        }

        return ResponseEntity.ok(inserted.get());
    }

    @DeleteMapping("/deleteAllApplications")
    public ResponseEntity<String> deleteAll(){
        jobApplicationService.deleteAllApplications();
        return ResponseEntity.ok("All job applications deleted");
    }

    @GetMapping("/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByPostId(id));
    }

    // Parameters could be changed to 2 @PathVariable by "getApplicationsByIds/JobPost/{post_id}/User/{user_id}"
    @GetMapping("/getApplicationByIds")
    public ResponseEntity<JobApplication> getApplicationByIds(@RequestParam Long post_id, @RequestParam Long user_id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationByIds(post_id, user_id));
    }
    

    
}
