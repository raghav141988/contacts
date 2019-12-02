package com.example.contactsbackend;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.extern.slf4j.Slf4j;



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
@Profile("!dev")
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
       antMatcher("/**")  
        .authorizeRequests()  
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
       
        .anyRequest().authenticated();
		
       
		http.oauth2ResourceServer();
        
       
    }
    
    
}