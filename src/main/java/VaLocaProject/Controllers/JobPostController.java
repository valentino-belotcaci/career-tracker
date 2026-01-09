package VaLocaProject.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Services.JobPostService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class JobPostController {
    
    @Autowired
    JobPostService jobPostService;


    @GetMapping("JobPost/getAllPosts")
    public ResponseEntity<List<JobPost>> getAllPosts() {
        return ResponseEntity.ok(jobPostService.getAllPosts());
    }
    
}
