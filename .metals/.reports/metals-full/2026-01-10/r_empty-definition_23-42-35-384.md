error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java:_empty_/JobPostRepository#findByCompany_id#
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java
empty definition using pc, found symbol in pc: _empty_/JobPostRepository#findByCompany_id#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2547
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
        if (jobPost.getCompany_id() == null || !companyRepository.existsById(jobPost.getCompany_id())) {
            throw new IllegalArgumentException("Company not found");
        }

        // Creates the current date to save (not fully working)
        jobPost.setCreated_at(new java.sql.Date(System.currentTimeMillis()));

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
        if (jobPost.getCompany_id() != null) {
            foundJobPost.setCompany_id(jobPost.getCompany_id());
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

    public List<JobPost> getPostsByCompany(Long id){
        Company foundcompany = companyRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Company not found"));
        return jobPostRepository.findByComp@@any_id(foundcompany.company_id);
    }

    public List<JobPost> getPostByCompanyid(Long id){
        Company foundcompany = companyRepository.findByAccountId(id);
        if (foundcompany == null) {
            return java.util.Collections.emptyList();
        }
        return jobPostRepository.findByCompany_id(foundcompany.company_id);
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/JobPostRepository#findByCompany_id#