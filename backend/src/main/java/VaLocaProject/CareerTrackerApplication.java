package VaLocaProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableCaching
@EnableMethodSecurity // Allows you to use @PreAuthorize security in Services
public class CareerTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerTrackerApplication.class, args);
	}

}
