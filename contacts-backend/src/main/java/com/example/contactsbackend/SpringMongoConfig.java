package com.example.contactsbackend;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SpringMongoConfig extends AbstractMongoClientConfiguration {
	
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port}")
    private String mongoPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDB;
    @Value("${spring.data.mongodb.username}")
    private String userName;
    @Value("${spring.data.mongodb.password}")
    private String password;
   
    @Override
    @Bean
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoClientDbFactory(mongoClient(), getDatabaseName());
	}
    @Override
	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

    }

	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		  String envHost = System.getenv("MONGODB_HOST");
		  String envUserName = System.getenv("MONGO_INITDB_ROOT_USERNAME");
		  String envPassword = System.getenv("MONGO_INITDB_ROOT_PASSWORD");
		  
    	  log.info("MONGODB_HOST="+envHost);
    	
    	String connectionString=  "mongodb://"+(envUserName==null?userName:envUserName)+":"+(envPassword==null?password:envPassword)+"@"+(envHost==null ? mongoHost : envHost)+ ":" + Integer.parseInt(mongoPort );
    	  log.info("connectionString="+connectionString);
		return   MongoClients.create(connectionString);
	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return mongoDB;
	}
}