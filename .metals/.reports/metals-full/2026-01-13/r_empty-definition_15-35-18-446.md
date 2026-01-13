error id: file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java:org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder#
file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java
empty definition using pc, found symbol in pc: org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 163
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java
text:
```scala
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.@@BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder#