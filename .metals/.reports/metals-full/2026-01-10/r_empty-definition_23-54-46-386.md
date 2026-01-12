error id: file://<WORKSPACE>/src/main/java/VaLocaProject/Repositories/UserRepository.java:_empty_/Query#value#
file://<WORKSPACE>/src/main/java/VaLocaProject/Repositories/UserRepository.java
empty definition using pc, found symbol in pc: _empty_/Query#value#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 334
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/Repositories/UserRepository.java
text:
```scala
package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.User;



public interface UserRepository extends JpaRepository<User, Long> {
        @Query(valu@@e = "SELECT * from users WHERE account_id = :accountId", nativeQuery=true)
        public User findByAccountId(@Param("accountId") Long id);
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Query#value#