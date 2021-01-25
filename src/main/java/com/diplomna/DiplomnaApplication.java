package com.diplomna;

import com.diplomna.api.TestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DiplomnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiplomnaApplication.class, args);
		/*
		Logger logger = LoggerFactory.getLogger(TestAPI.class);
		logger.warn("information");

		 */
	}

}
