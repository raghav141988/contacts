package com.example.contactsbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.contactsbackend.model.Contact;

public interface ContactsRepository extends MongoRepository<Contact, String> {

}
