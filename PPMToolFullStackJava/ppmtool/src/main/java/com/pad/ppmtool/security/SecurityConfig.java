package com.pad.ppmtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pad.ppmtool.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JwtAuthenticationEntryPoint unauthorizedEntryPoint;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}



	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.headers().frameOptions().sameOrigin()
		.and()
		.authorizeRequests()
		.antMatchers("/",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js")
		.permitAll()
		.antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll()
		.anyRequest().authenticated();
	}

}
