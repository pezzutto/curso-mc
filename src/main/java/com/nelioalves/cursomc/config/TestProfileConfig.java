package com.nelioalves.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nelioalves.cursomc.services.DBService;
import com.nelioalves.cursomc.services.EmailService;
import com.nelioalves.cursomc.services.MockEmailService;
import com.nelioalves.cursomc.services.SmtpEmailService;

@Configuration
@Profile("test")
public class TestProfileConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws Exception {
		
		
		dbService.instatiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		//return new MockEmailService();
		return new SmtpEmailService();
	}
	
}
