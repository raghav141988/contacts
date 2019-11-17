package com.example.contactsbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.repository.ContactsRepository;

@Component
public class ContactsService {

	
	@Autowired
	ContactsRepository contactRepository;

	
	public Iterable<Contact> findAll() {
		return contactRepository.findAll();
	}

	
	public Contact save(Contact student) {
		return contactRepository.save(student);
	}

	
	public Optional<Contact> findByID(String id) {
		return contactRepository.findById(id);
	}


	public void delete(String id) {
		Contact contact = new Contact();
		contact.setId(id);
		contactRepository.delete(contact);		
	}


}
