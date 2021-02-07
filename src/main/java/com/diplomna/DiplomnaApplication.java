package com.diplomna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiplomnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiplomnaApplication.class, args);

		/*
		//run second thread for notifications
		NotificationRunner notificationRunner = new NotificationRunner();
		try {
			notificationRunner.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 */
	}

}
