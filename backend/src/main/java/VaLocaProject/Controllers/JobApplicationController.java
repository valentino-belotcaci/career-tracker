package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;



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
    public ResponseEntity<JobApplication> insertApplication(@RequestBody JobApplication jobApplication) {
       return ResponseEntity.ok(jobApplicationService.insertApplication(jobApplication));
 
    }

    @DeleteMapping("/deleteAllApplications")
    public ResponseEntity<String> deleteAll(){
        jobApplicationService.deleteAllApplications();
        return ResponseEntity.ok("All job applications deleted");
    }

    // For companies
    @GetMapping("/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByPostId(id));
    }

    // Parameters could be changed to 2 @PathVariable by "getApplicationsByIds/JobPost/{post_id}/User/{user_id}"
    @GetMapping("/getApplicationByIds")
    public ResponseEntity<JobApplication> getApplicationByIds(@RequestParam Long post_id, @RequestParam Long user_id) {

        return jobApplicationService.getApplicationByIds(post_id, user_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    // For users
    @GetMapping("/getApplicationsByUserId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByUserId(id));
    }

    @GetMapping("/getApplicationById/{id}")
    public ResponseEntity<JobApplication> getApplicationById(@PathVariable Long id) {
        return jobApplicationService.getApplicationById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

}
