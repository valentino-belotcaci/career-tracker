error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java:java/sql/Date#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java
empty definition using pc, found symbol in pc: java/sql/Date#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 915
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java
text:
```scala
package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.JobPostRepository;

@Service
public class JobPostService {
    
    @Autowired
    JobPostRepository jobPostRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<JobPost> getAllPosts(){
        return jobPostRepository.findAll();
    }

    public JobPost insertPost(JobPost jobPost){
        // Validate company exists
        if (jobPost.getCompanyId() == null) {
            throw new IllegalArgumentException("Company not found");
        }

        // Creates the current date to save (not fully working)
        jobPost.setCreatedAt(new java.sql.Da@@te(System.currentTimeMillis()));

        return jobPostRepository.save(jobPost);
    }

    public void deleteAllPosts(){
        jobPostRepository.deleteAll();
    }

    public void deletePost(Long id){
        jobPostRepository.deleteById(id);
    }

    public JobPost updatePost(Long id, JobPost jobPost){
        JobPost foundJobPost = jobPostRepository.findById(id).orElseThrow(
            () -> new RuntimeException("JobPost not found"));
        // Check each field and update when non-null (or non-zero for primitives)
        if (jobPost.getCompanyId() != null) {
            foundJobPost.setCompanyId(jobPost.getCompanyId());
        }

        if (jobPost.getName() != null) {
            foundJobPost.setName(jobPost.getName());
        }

        if (jobPost.getDescription() != null) {
            foundJobPost.setDescription(jobPost.getDescription());
        }

        if (jobPost.getDuration() != null) {
            foundJobPost.setDuration(jobPost.getDuration());
        }

        if (jobPost.getAvailable() != null) {
            foundJobPost.setAvailable(jobPost.getAvailable());
        }

        if (jobPost.getSalary() != 0) {
            foundJobPost.setSalary(jobPost.getSalary());
        }

        // Persist and return the updated entity
        return jobPostRepository.save(foundJobPost);
    }


    public List<JobPost> getPostsByCompanyId(Long id){
        // Checks if the company exists
        Company foundcompany = companyRepository.findByAccountId(id);
        
        return jobPostRepository.findByCompanyId(foundcompany.getCompanyId());
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/sql/Date#