package com.example.contactsbackend;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;



@EnableWebSecurity
@Profile("dev")
@Slf4j
public class SecurityDevConfiguration extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception{
	        http.cors().and().csrf().disable();
	        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
	        http.cors().configurationSource(request -> new CorsConfiguration(corsConfiguratione()));
	    }
	    
	    
	 
	 @Bean
	 CorsConfiguration corsConfiguratione() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE"));
	       
	      
	        log.info("CORS Configuration is complete");
	        return configuration;
	    }
	 
    
}