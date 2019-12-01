package com.example.contactsexternal.apigateway.client;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.contactsexternal.apigateway.model.Contact;

@FeignClient(name="contacts" , url = "${contact.service.url}")
public interface ContactsClient {
	@GetMapping("/contacts")
    List<Contact> contacts();
	@GetMapping(value = "/contacts/{id}")
	Optional<Contact> getContact(String id);
	
	@PostMapping(value = "/contacts",  consumes =APPLICATION_JSON_VALUE)
	public Contact saveContact(Contact contact) ;
	

	@DeleteMapping("/contacts/{id}")

	public void deleteContact( String id);
	
	
}
