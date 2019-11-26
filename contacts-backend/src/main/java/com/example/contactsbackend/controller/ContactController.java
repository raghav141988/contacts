package com.example.contactsbackend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.model.TokenRequest;
import com.example.contactsbackend.service.ContactsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class ContactController {

	 final String uri = "https://dev-490248.okta.com/oauth2/default/v1/token";
	 
	 @Value("${okta.oauth2.client-id}")
	    private String clientId;
	    @Value("${okta.oauth2.client-secret}")
	    private String clientSecret;
	 
	@Autowired

	ContactsService contactService;
	@RequestMapping("/")  
    @ResponseBody  
    public String home() {  
        return "Welcome home!";  
    }  
	
	@RequestMapping("/login")  
    @ResponseBody  
    public String login() {  
        return "Welcome home!";  
    } 
	
	
	@PostMapping(path = "/token", consumes = "application/x-www-form-urlencoded")
	public String test(String  code) {
		 
        TokenRequest request = new TokenRequest();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
       request.setGrant_type("grant_type");
       request.setScope("openid group");
       request.setState(UUID.randomUUID().toString());
       request.setCode(code);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject( uri, request, String.class);
	}
	
	
	
	
	
	@RequestMapping("/oauthinfo")  
    @ResponseBody  
    public String oauthUserInfo(
                              @AuthenticationPrincipal OAuth2User oauth2User) {  
        return  
        		
            "User Name: " + oauth2User.getName() + "<br/>" +  
            "User Authorities: " + oauth2User.getAuthorities() + "<br/>" +  
           // "Client Name: " + authorizedClient.getClientRegistration().getClientName() + "<br/>" +  
            this.prettyPrintAttributes(oauth2User.getAttributes());  
    }  
	 private String prettyPrintAttributes(Map<String, Object> attributes) {  
	        String acc = "User Attributes: <br/><div style='padding-left:20px'>";  
	        for (String key : attributes.keySet()){  
	            Object value = attributes.get(key);  
	            acc += "<div>"+key + ":&nbsp" + value.toString() + "</div>";  
	        }  
	        return acc + "</div>";  
	    }  
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<Contact> contacts() {
		
		return contactService.findAll();
	}
	
	@PreAuthorize("hasAuthority('Admin')")  
	@RequestMapping("/restricted")  
	@ResponseBody  
	public String restricted() {  
	    return "You found the secret lair!";  
	}
	
	@GetMapping(value = "/contacts/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Optional<Contact> fetchContact(@PathVariable String id) {
		
		return contactService.findByID(id);
	}
	
	
	@PostMapping(value = "/contacts",  consumes =APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public Contact saveContact(@RequestBody Contact contact) {
		log.info("Contact data:: "+contact.toString());
		return contactService.save(contact);
	}
	

	@DeleteMapping("/contacts/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteContact(@PathVariable String id) {
		 contactService.delete(id);
		
	}
	

}
