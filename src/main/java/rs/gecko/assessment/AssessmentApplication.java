package rs.gecko.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author nikolak
 *
 *         The Weather program implements an application that get information
 *         from two APIs and combined them to display information about city
 *         weather and city coordinates for public and logged users. Logged user
 *         can edit, delete and add new cities and API configurations for
 *         application.
 * 
 */

@SpringBootApplication
@EnableJpaRepositories("rs.gecko.repositories")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}
}
