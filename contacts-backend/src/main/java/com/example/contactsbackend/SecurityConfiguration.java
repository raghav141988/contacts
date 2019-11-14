package com.example.contactsbackend;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@EnableWebSecurity
@Profile("!dev")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable();
       
    }
    
    
}