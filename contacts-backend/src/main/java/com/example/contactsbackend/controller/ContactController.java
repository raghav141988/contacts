package com.example.contactsbackend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.service.ContactsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/")
public class ContactController {

	@Autowired

	ContactsService contactService;


	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<Contact> contacts() {
		
		return contactService.findAll();
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
