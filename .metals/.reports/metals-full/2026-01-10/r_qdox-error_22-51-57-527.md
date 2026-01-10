error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[95,1]

error in qdox parser
file content:
```java
offset: 3062
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
        if (jobPost.getCompanyId() == null || !companyRepository.existsById(jobPost.getCompanyId())) {
            throw new IllegalArgumentException("Company not found");
        }

        // Ensure createdAt is set 
        if (jobPost.getCreatedAt() == null) {
            jobPost.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

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

        // For salary (primitive int) only update when non-zero
        if (jobPost.getSalary() != 0) {
            foundJobPost.setSalary(jobPost.getSalary());
        }

        // Persist and return the updated entity
        return jobPostRepository.save(foundJobPost);
    }

    public List<JobPost> getPostByCompany(Long id){
        Company foundcompany = companyRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Company not found"));
        return jobPostRepository.findByCompanyId(foundcompany.company_id);
    }

    // New: get posts by company email. Returns empty list when company not found.
    public List<JobPost> getPostByCompanyEmail(String email){
        Company foundcompany = companyRepository.findByEmail(email);
        if (foundcompany == null) {
            return java.util.Collections.emptyList();
        }
        return jobPostRepository.findByCompanyId(foundcompany.company_id);
    }
}
@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:546)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:677)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:674)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:630)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:628)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1313)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:674)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:912)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:691)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:500)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

QDox parse error in file://<WORKSPACE>/src/main/java/VaLocaProject/Services/JobPostService.java