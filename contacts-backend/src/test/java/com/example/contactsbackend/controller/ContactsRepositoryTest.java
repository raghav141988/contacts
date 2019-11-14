package com.example.contactsbackend.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.contactsbackend.config.FakeMongo;
import com.example.contactsbackend.model.Contact;
import com.example.contactsbackend.repository.ContactsRepository;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Anand_Rajneesh on 6/14/2017. TODO FIXME - Text based search
 * queries are not working in test mode, Fongo not able to set up text index ?
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@Import(value = { FakeMongo.class })
@DataMongoTest
@EnableMongoRepositories(basePackageClasses = { ContactsRepository.class })
@Slf4j
public class ContactsRepositoryTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ContactsRepository contactsRepository;

//	@Rule
//	public MongoDbRule embeddedMongoDbRule = newMongoDbRule().defaultSpringMongoDb("mockDB");

	@Test
	@UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
	public void noStudentsTest() {
		List<Contact> contacts = contactsRepository.findAll();
		assertTrue("Returned book list should be empty", contacts.isEmpty());
	}
	
	@Test
	@UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
	public void saveStudentsTest() {
		int qt = 10;
		for (int i = 0; i < qt; i++) {
			contactsRepository.save(new Contact());		
		}
		List<Contact> students = contactsRepository.findAll();
		assertEquals(qt, students.size());
		contactsRepository.deleteAll();
	}
	
	@Test
	@UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
	public void findById() {
		contactsRepository.save(new Contact("1234", "raghav", null, null, null));
		List<Contact> students = contactsRepository.findAll();
		log.info("Size:: "+students.size());
		Optional<Contact> st = contactsRepository.findById(students.get(0).getId());
		assertTrue(st.isPresent());
		log.info("Contact:: "+st.get());
		assertEquals("raghav", st.get().getName());
	}

}