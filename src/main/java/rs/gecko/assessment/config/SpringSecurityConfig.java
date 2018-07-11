package rs.gecko.assessment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/h2-console/**").disable().authorizeRequests().antMatchers("/**/favicon.ico")
				.permitAll().and().authorizeRequests().antMatchers("/cities/**").permitAll().and().authorizeRequests()
				.antMatchers("/webjars/**").permitAll().and().authorizeRequests().antMatchers("/static/css").permitAll()
				.and().authorizeRequests().antMatchers("/img/**").permitAll().and().authorizeRequests()
				.antMatchers("/js").permitAll().and().formLogin().loginPage("/login").permitAll().and()
				.authorizeRequests().antMatchers("/admin/**").authenticated().and().authorizeRequests()
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN").and().exceptionHandling()
				.accessDeniedPage("/access_denied");

		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/h2-console/**")
				.permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
