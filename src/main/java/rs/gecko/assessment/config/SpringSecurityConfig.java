package rs.gecko.assessment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

//	@Autowired
//	@Qualifier("daoAuthenticationProvider")
//	private AuthenticationProvider authenticationProvider;
//	
//	
////	@Bean
////    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
////                                                               UserDetailsService userDetailsService){
////
////        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
////        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
////        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
////        return daoAuthenticationProvider;
////    }
//
//    @Autowired
//    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/h2-console/**").disable()
        		.authorizeRequests().antMatchers("/**/favicon.ico") .permitAll()
                .and().authorizeRequests().antMatchers("/cities/**").permitAll()
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/css").permitAll()
                .and().authorizeRequests().antMatchers("/img/**").permitAll()
                .and().authorizeRequests().antMatchers("/js").permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and().authorizeRequests().antMatchers("/admin/**").authenticated()
                .and().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .and().exceptionHandling().accessDeniedPage("/access_denied");
        
        http.authorizeRequests().antMatchers("/").permitAll().and()
        .authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
	
}
