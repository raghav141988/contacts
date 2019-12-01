package com.example.contactsexternal.apigateway.client;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.contactsexternal.apigateway.model.Contact;

@FeignClient(name="contacts" , url = "${contact.service.url}")
public interface ContactsClient {
	@GetMapping("/contacts")
    List<Contact> contacts();
	@GetMapping(value = "/contacts/{id}")
	Optional<Contact> getContact(@PathVariable("id")  String id);
	
	@PostMapping(value = "/contacts",  consumes =APPLICATION_JSON_VALUE)
	public Contact saveContact(Contact contact) ;
	

	
	@RequestMapping(method = RequestMethod.DELETE, value = "/contacts/{id}")
	public @ResponseBody void deleteContact(@PathVariable("id") String id);
	
	
}
