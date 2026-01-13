package VaLocaProject.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Services.JobPostService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/JobPost")

public class JobPostController {
    
    @Autowired
    JobPostService jobPostService;


    @GetMapping("/getAllPosts")
    public ResponseEntity<List<JobPost>> getAllPosts() {
        return ResponseEntity.ok(jobPostService.getAllPosts());
    }

    @PostMapping("/insertPost")
    public ResponseEntity<JobPost> insertPost(@RequestBody JobPost jobPost) {   
        return ResponseEntity.ok(jobPostService.insertPost(jobPost));
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        jobPostService.deletePost(id);
        return ResponseEntity.ok("The post has been deleted");
    }

    @DeleteMapping("/deleteAllPosts")
    public ResponseEntity<String> deleteAllPosts(){
       jobPostService.deleteAllPosts();
        return ResponseEntity.ok("All posts deleted");
    }

    @GetMapping("/getPostsByCompanyId/{id}")
    public ResponseEntity<List<JobPost>> getPostsByCompanyId(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostService.getPostsByCompanyId(id));
    }
    

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<JobPost> updatePost(@PathVariable Long id, @RequestBody JobPost jobPost) {
        return ResponseEntity.ok(jobPostService.updatePost(id, jobPost));
    }
    
    
}
