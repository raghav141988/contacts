package com.example.contactsbackend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.model.UserInfo;
import com.example.contactsbackend.service.ContactsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class ContactController {

	 final String uri = "https://dev-490248.okta.com/oauth2/default/v1/token";
	 
	
	   
	 
	@Autowired

	ContactsService contactService;

	
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public Iterable<Contact> contacts(@AuthenticationPrincipal Principal userInfo) {
		log.info("UserInfo"+userInfo);
		return contactService.findAll();
	}
	

	
	@GetMapping(value = "/contacts/{id}")
	public Optional<Contact> fetchContact(@PathVariable String id,@AuthenticationPrincipal Principal userInfo) {
		
		return contactService.findByID(id);
	}
	
	
	@PostMapping(value = "/contacts",  consumes =APPLICATION_JSON_VALUE)
	public Contact saveContact(@RequestBody Contact contact,@AuthenticationPrincipal Principal userInfo) {
		log.info("Contact data:: "+contact.toString());
		log.info("User Name:: "+userInfo.getName());
		return contactService.save(contact);
	}
	

	@DeleteMapping("/contacts/{id}")

	public void deleteContact(@PathVariable String id,@AuthenticationPrincipal Principal userInfo) {
		 contactService.delete(id);
		
	}
	

}
