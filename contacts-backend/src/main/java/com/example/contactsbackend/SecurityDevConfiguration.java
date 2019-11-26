package com.example.contactsbackend;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import com.okta.spring.boot.oauth.Okta;

import lombok.extern.slf4j.Slf4j;



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
@Profile("dev")
@Slf4j
public class SecurityDevConfiguration extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception{
		 http.antMatcher("/**")  
         .authorizeRequests()  
         .antMatchers("/").permitAll()  
         .anyRequest().authenticated()  
         .and().oauth2Client()
         .and().oauth2Login();
     
    
	    }
	    
	    
	 
	 @Bean
	 CorsConfiguration corsConfiguratione() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE"));
	       
	      
	        log.info("CORS DEV Configuration is complete");
	        return configuration;
	    }
	 
    
}