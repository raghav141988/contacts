package com.example.contactsexternal.apigateway.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactsexternal.apigateway.client.ContactsClient;
import com.example.contactsexternal.apigateway.model.Contact;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/")

public class ContactsController {

	@Autowired

	ContactsClient contactsClient;

	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public Iterable<Contact> contacts(@AuthenticationPrincipal Principal principal) {
		
		
		return contactsClient.contacts();
	}

	@GetMapping(value = "/contacts/{id}")
	
	public Optional<Contact> fetchContact(@PathVariable String id,@AuthenticationPrincipal Principal principal) {
		return null;
      
		//return contactsClient.getContact(id);
	}

	@PostMapping(value = "/contacts", consumes = APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('Admin')")
	
	public Contact saveContact(@RequestBody Contact contact,@AuthenticationPrincipal Principal principal) {
		log.info("Contact data:: " + contact.toString());
		
		return contactsClient.saveContact(contact);
	}

	@DeleteMapping("/contacts/{id}")
	@PreAuthorize("hasAuthority('Admin')")
	
	public void deleteContact(@PathVariable String id,@AuthenticationPrincipal Principal principal) {
		
	        contactsClient.deleteContact(id);

	}
	

}
