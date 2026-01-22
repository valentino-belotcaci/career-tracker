    package VaLocaProject.Security;

    import java.util.Arrays;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


    @Configuration
    public class SecurityConfig {

        @Autowired
        private JWTFilter jwtFilter;
        
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
                // Disable csrf token as we are using JWT stateless session managing
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request 
                // static pages and public resources - allow the browser to GET the HTML (client will attach JWT for API calls)
                .requestMatchers(
                    "/index.html",
                    "/register.html",
                    "/login.html",
                    "/",
                    "/indexUser.html",
                    "/indexCompany.html",
                    "/profileUser.html",
                    "/profileCompany.html",
                    "/displayJobPost.html",
                    "/jobPostDetails.html",
                    "/createJobApplication.html",
                    "/displayJobApplication.html",
                    "/createJobPost.html",
                    "/css/**",
                    "/favicon.ico",
                    "/error",
                    "/Account/authenticate",
                    "/Account/insertAccount",
                    "/Account/getAccountByEmail/**",
                    "/User/getUserByAccountId/**",
                    "/JobApplication/getApplicationsByUserId/**",
                    "/JobPost/getAllPosts",
                    "/JobPost/insertPost/**",
                    "/JobApplication/getApplicationByIds",
                    "/User/updateUser/**",
                    "/Company/updateCompany/**",
                    "/Company/getCompanyByAccountId/**",
                    "/JobPost/getPostsByCompanyId/**",
                    "/JobApplication/getApplicationsByPostId/**"
                    
                ).permitAll() 

                // Keep API endpoints protected by roles. For example, replace these with your API paths:
                // .requestMatchers("/api/users/**").hasRole("USER")
                // .requestMatchers("/api/companies/**").hasRole("COMPANY")
                // ...existing API protection rules...
                
                // Allow OPTIONS for CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
        AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource(){
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:8080"
            ));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            configuration.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }


    }