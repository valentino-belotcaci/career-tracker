error id: file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java:_empty_/`<any>`#requestMatchers#permitAll#anyRequest#
file://<WORKSPACE>/src/main/java/VaLocaProject/SecurityConfig.java
empty definition using pc, found symbol in pc: _empty_/`<any>`#requestMatchers#permitAll#anyRequest#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 832
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
            .csrf(csrf -> csrf.disable()) // disable for simple frontend fetches; enable with token flow in prod
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // allow onboarding page
                .anyRe@@quest().authenticated() // all other pages need login
            )
            .formLogin(form -> form
                .loginPage("/login.html") // optional: your custom login page
                .loginProcessingUrl("/login")        // URL the form must POST to
                .usernameParameter("email")       // default, change if form uses different name
                .passwordParameter("password")
                .defaultSuccessUrl("/indexUser.html", true)
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/`<any>`#requestMatchers#permitAll#anyRequest#