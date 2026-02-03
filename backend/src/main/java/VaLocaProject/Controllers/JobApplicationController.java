package VaLocaProject.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;




@RestController
@RequestMapping("/JobApplication")
public class JobApplicationController {
    
    
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

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

    @DeleteMapping("/deleteApplication/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable UUID id){
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok("The job application has been deleted");
    }
    // For companies
    @GetMapping("/getApplicationsByPostId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationsByPostId(@PathVariable UUID id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByPostId(id));
    }

    // Parameters could be changed to 2 @PathVariable by "getApplicationsByIds/JobPost/{post_id}/User/{user_id}"
    @GetMapping("/getApplicationByIds")
    public ResponseEntity<JobApplication> getApplicationByIds(@RequestParam UUID post_id, @RequestParam UUID user_id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationByIds(post_id, user_id));
    }
    
    // For users
    @GetMapping("/getApplicationsByUserId/{id}")
    public ResponseEntity<List<JobApplication>> getApplicationByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByUserId(id));
    }

    @GetMapping("/getApplicationById/{id}")
    public ResponseEntity<JobApplication> getApplicationById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationById(id));
    }

    @PutMapping("/updateApplication/{id}")
    public ResponseEntity<JobApplication> updateApplication(@PathVariable UUID id, @RequestBody JobApplication jobApplication) {
        return ResponseEntity.ok(jobApplicationService.updateApplication(id, jobApplication));
    }
}
