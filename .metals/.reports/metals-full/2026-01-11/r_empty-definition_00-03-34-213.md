error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobPostController.java:_empty_/JobPost#
file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobPostController.java
empty definition using pc, found symbol in pc: _empty_/JobPost#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 863
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Controllers/JobPostController.java
text:
```scala
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
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class JobPostController {
    
    @Autowired
    JobPostService jobPostService;


    @GetMapping("JobPost/getAllPosts")
    public ResponseEntity<List<JobP@@ost>> getAllPosts() {
        return ResponseEntity.ok(jobPostService.getAllPosts());
    }

    @PostMapping("JobPost/insertPost")
    public ResponseEntity<JobPost> insertPost(@RequestBody JobPost jobPost) {   
        return ResponseEntity.ok(jobPostService.insertPost(jobPost));
    }

    @DeleteMapping("JobPost/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        jobPostService.deletePost(id);
        return ResponseEntity.ok("The post has been deleted");
    }

    @DeleteMapping("JobPost/deleteAllPosts")
    public ResponseEntity<String> deleteAllPosts(){
       jobPostService.deleteAllPosts();
        return ResponseEntity.ok("All posts deleted");
    }

    @GetMapping("JobPost/getPostsByCompanyId/{id}")
    public ResponseEntity<List<JobPost>> getPostsByCompanyId(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostService.getPostsByCompanyId(id));
    }
    

    @PutMapping("JobPost/updatePost/{id}")
    public ResponseEntity<JobPost> updatePost(@PathVariable Long id, @RequestBody JobPost jobPost) {
        return ResponseEntity.ok(jobPostService.updatePost(id, jobPost));
    }
    
    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JobPost#