package com.example.contactsbackend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.MalformedURLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.oauth.config.MsalAuthHelper;
import com.example.contactsbackend.service.ContactsService;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/")
public class ContactController {
    @Autowired
    MsalAuthHelper msalAuthHelper;
	@Autowired
	ContactsService contactService;

	 
	 
	    @PreAuthorize("hasRole('ROLE_backendAppGroup')")
	    @GetMapping("/contcts")
	    public Iterable<Contact> contacts() throws MalformedURLException{
	        
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
   	
	    	return contactService.findAll();
	    }
	    
	  



	
	@GetMapping(value = "/contcts/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Optional<Contact> fetchContact(@PathVariable String id) {
		
		return contactService.findByID(id);
	}
	
	
	@PostMapping(value = "/contcts",  consumes =APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public Contact saveContact(@RequestBody Contact contact) {
		log.info("Contact data:: "+contact.toString());
		return contactService.save(contact);
	}
	

	@DeleteMapping("/contcts/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteContact(@PathVariable String id) {
		 contactService.delete(id);
		
	}
	
	
	

}
