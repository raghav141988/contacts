package com.example.contactsbackend;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;



@EnableWebSecurity
@Profile("dev")
@Slf4j
public class SecurityDevConfiguration extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception{
		  http.cors();
	        http.csrf().disable();
	        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
	      ;
	    }
	    
	    
	 
	
	 
    
}