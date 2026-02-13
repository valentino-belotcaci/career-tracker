package VaLocaProject.Security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity // Explicitly tell spring here there is a security configuration 
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            // As we are using cookie to send the JWT we need csrf protection
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/ws/**")
                
            // withHttpOnlyFalse allows React to read the CSRF token string
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
                
            // We need to call this bean to force the lazy csrf token creation at the first request       
            .csrfTokenRequestHandler(createSpaRequestHandler())
            // Add this so that the csrf token is not necessary for login and register
            .ignoringRequestMatchers("/Account/authenticate", "/Account/insertAccount", "/Account/logout")
            )
            .authorizeHttpRequests(request -> request 

            // static pages and public resources - allow the browser to GET the HTML (client will attach JWT for API calls)
            .requestMatchers(
                "/index.html",                    
                "/indexUser.html",
                "/register.html",
                "/login.html",
                "/indexCompany.html",
                "/profileUser.html",
                "/profileCompany.html",
                "/displayJobPost.html",
                "/jobPostDetails.html",
                "/createJobApplication.html",
                "/displayJobApplication.html",
                "/createJobPost.html",
                "/JobApplicationDetails.html",

                "/",
                "/css/**",
                "/favicon.ico",
                "/error",

                "/Account/authenticate",
                "/Account/insertAccount",

                "/ws/**"
            ).permitAll()

            .requestMatchers(
                "/Account/getAccountById/**",
                "/Account/getAccountByEmail/**",
                "/Account/updateAccount/**",
                "/Account/deleteAccount/**",
                "/Account/getAllAccounts",
                "/JobPost/getPostById/**",
                "/JobApplication/getApplicationById/**"
            ).hasAnyRole("USER", "COMPANY")
            
            .requestMatchers(
                "/JobPost/insertJobPost",
                "/JobPost/updateJobPost/**",
                "/JobPost/deleteJobPost/**",
                "/JobPost/getPostsByCompanyId",
                "/JobApplication/getApplicationsByPostId/**"
            ).hasRole("COMPANY")

            .requestMatchers(
                "/JobApplication/insertJobApplication",
                "/JobApplication/updateJobApplication/**",
                "/JobApplication/deleteJobApplication/**",
                "/JobApplication/getApplicationsByUserId/**",
                "/JobPost/getAllJobPosts"
            ).hasRole("USER")
            

            
            
            
            // Allow OPTIONS for CORS preflight
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
            )
            // To add the JWT filter as first filter, to not even touch the backend without it
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            // Add csrf filter after JWT but still before the normal authentication
            .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
            // Define the sesionManagemant as STATELESS (STATEFULL as default)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .formLogin(form -> form.disable())
            .logout(logout -> logout.permitAll());

        return http.build();
    }
    

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",
            "http://localhost:8080",
            "https://localhost:8443"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "X-XSRF-TOKEN"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    // Helper needed to manually define a new csrf token with null name 
    // to bypass the lazy loading of the token and send it at the first request
    @Bean
    CsrfTokenRequestHandler createSpaRequestHandler() {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName(null);
        return requestHandler;
    }

}