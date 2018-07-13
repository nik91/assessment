package rs.gecko.assessment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {







	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("karec91").password("{noop}nikola91").roles("ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().ignoringAntMatchers("/h2-console/**").disable().authorizeRequests().antMatchers("/**/favicon.ico")
				.permitAll()

				.and().authorizeRequests().antMatchers("/cities/**").permitAll()

				.and().authorizeRequests().antMatchers("/webjars/**").permitAll()

				.and().authorizeRequests().antMatchers("/static/css").permitAll()

				.and().authorizeRequests().antMatchers("/img/**").permitAll()

				.and().authorizeRequests().antMatchers("/js").permitAll()

				.and().formLogin().loginPage("/login").permitAll()

				// .and().authorizeRequests().antMatchers("/admin/**").authenticated()

				// .and().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN")

				.and().exceptionHandling().accessDeniedPage("/access_denied");

		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/h2-console/**")
				.permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
