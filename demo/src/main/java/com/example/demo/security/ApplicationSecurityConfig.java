package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
	
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/","index","/css/*","/js/*")
		.permitAll()
		.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
		.anyRequest().authenticated()
		.and()
		.httpBasic();
		}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails silpaUser = User.builder()
				.username("silpa")
				.password(passwordEncoder.encode("pass"))
				.roles(ApplicationUserRole.STUDENT.name())
				.build();
		
		UserDetails samUser = User.builder()
				.username("sam")
				.password(passwordEncoder.encode("pass"))
				.roles(ApplicationUserRole.ADMIN.name())
				.build();
		
		return new InMemoryUserDetailsManager(silpaUser,samUser);
				
	}
	
	

}
