package VaLocaProject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable()) 
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request 
            // ALLOW FOR ALL USERS
            .requestMatchers(
                "/index.html",
                "/register.html",
                "/login.html",
                "/",
                "/Account/authenticate",
                "/Account/insertAccount",
                "/css/**",
                "/favicon.ico",
                "/error"
            ).permitAll() 

            // ALLOW ONLY FOR "USER"
            .requestMatchers(
                "/indexUser.html",
                "/displayJobApplication.html",
                "/createJobApplication.html",
                "/profileUser.html"
            ).hasRole("USER")

            // ALLOW ONLY FOR "COMPANY"
            .requestMatchers(
                "/indexCompany.html",
                "/profileCompany.html"
            ).hasRole("COMPANY")

            // // ALLOW ONLY FOR "USER or COMPANY"
            .requestMatchers(
                "/displayJobPost.html",
                "/jobPostDetails.html"
            ).hasAnyRole("USER", "COMPANY")

            .requestMatchers(
                "/**"
            ).hasRole("ADMIN")

            .anyRequest().authenticated()
            )
            
            // Define the sesionManagemant as STATELESS (STATEFULL as default)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // To add the JWT filter as first filter, to not even touch the backend without it
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .logout(logout -> logout.permitAll());

        return http.build();
    }
    

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }


}