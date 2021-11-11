package com.episen.ms;

import com.episen.ms.model.UserContext;
import com.episen.ms.security.JwTokenGenerator;
import com.episen.ms.security.JwTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MsCours1Application implements CommandLineRunner{

	@Autowired
	JwTokenGenerator generator;

	@Autowired
	JwTokenValidator validator;

	public static void main(String[] args) {
		SpringApplication.run(MsCours1Application.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		String token = generator.generateToken("efollet", Arrays.asList("ADMIN", "MANAGER"));
		System.out.println(token);

		//UserContext user = validator.transforme(token);
		//System.out.println(user);
	}

}
