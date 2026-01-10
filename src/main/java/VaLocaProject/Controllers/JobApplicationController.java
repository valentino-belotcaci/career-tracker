package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;

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
