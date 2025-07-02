package com.vik.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vik.filter.JWTFilter;

// To make this class as configuration class 
@Configuration

// To enable method level security in RestController class
@EnableMethodSecurity

// To enable spring security to project
@EnableWebSecurity
public class SecurityConfig 
{
	// @Autowired for Dependency injection by IOC container
	@Autowired
	private UserDetailsService service;
	
	// @Autowired for Dependency injection by IOC container
	@Autowired
	private JWTFilter jwtfilter;
	
	
	// Method for Security filter chaining
	// 1. JWTFilter
	// 2. UsernamePasswordAuthenticationFilter
	// @Bean for making method returned object as Spring Bean 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
	{
		return security
			   .csrf(Customizer -> Customizer.disable()) // To disable CSRF token
			   .authorizeHttpRequests(request -> request
			   .requestMatchers("register","login","home").permitAll() // To permit specific requests
			   .anyRequest().authenticated()) // To Enable authentication on other requests 
			   .httpBasic(Customizer.withDefaults()) // This gives response without HTML form good for Postman Testing
			   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			   .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class) // Adding one more layer of filter before current layer 
			   .build(); // Build the HttpSecurity object
	}
	
	
	// Method to define our choice AuthenticationProvider
	// and define BCryptPasswordEncoder to regenrate the password from HASH values for user Authentication
	// @Bean for making method returned object as Spring Bean 
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(service);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	
	
	// Method to return AuthenticationManagenr
	// @Bean for making method returned object as Spring Bean 
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
	
	
}
