error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[30,1]

error in qdox parser
file content:
```java
offset: 751
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java
text:
```scala
package VaLocaProject.Services;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.AccountRepository;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;

@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

 @@   public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    public Account insertAccount(Account account){

        // Hash password before saving using SHA-256 and Base64 encoding
        if (account.getPassword() != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(account.getPassword().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                String hashed = Base64.getEncoder().encodeToString(hash);
                account.setPassword(hashed);
            } catch (NoSuchAlgorithmException e) {
                // If hashing isn't available, continue with the plain password (unlikely)
            }
        }
        Account saved = accountRepository.save(account);



        // After creating the Account, also create a blank Company or User linked via account_id
        if (saved != null) {
            String type = saved.type;
            if (type.equals("COMPANY")) {
                // Company constructor order: company_id, name, email, description, account_id
                Company c = new Company(null, "", saved.getEmail(), "", saved.getAccountId());
                companyRepository.save(c);
            } else if (type.equals("USER")) {
                // User constructor order: user_id, name, email, description, account_id
                User u = new User(null, "", saved.getEmail(), "", saved.getAccountId());
                userRepository.save(u);
            }
    public Boolean authenticate(String email, String password){
        // Search account instance based on the email
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            return false;
        }
        
        // Check if the password matches using SHA-256 hash comparison
        if (account.getPassword() == null) return false;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            String hashed = Base64.getEncoder().encodeToString(hash);
            return account.getPassword().equals(hashed);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
    }

    public Boolean authenticate(String email, String password){
        // Search account instance based on the email
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            return false;
        }
        
        // Check if the password matches 
        // TODO: add password hashing
        if (account.password == null) return false;
        return account.getPassword().equals(password);
    }

    public void deleteAllAccounts(){
        accountRepository.deleteAll();
    }


}

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

QDox parse error in file://<WORKSPACE>/src/main/java/VaLocaProject/Services/AccountService.java