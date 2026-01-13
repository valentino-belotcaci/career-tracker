error id: file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java:_empty_/HttpSecurity#authorizeHttpRequests#formLogin#logout#
file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java
empty definition using pc, found symbol in pc: _empty_/HttpSecurity#authorizeHttpRequests#formLogin#logout#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 965
uri: file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java
text:
```scala
package VaLocaProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/",  "/login").permitAll() // allow onboarding page
                .anyRequest().authenticated() // all other pages need login
            )
            .formLogin(form -> form
                .loginPage("/login") // optional: your custom login page
                .permitAll()
            )
            .log@@out(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/HttpSecurity#authorizeHttpRequests#formLogin#logout#