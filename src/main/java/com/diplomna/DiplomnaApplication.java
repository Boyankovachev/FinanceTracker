package com.diplomna;

import com.diplomna.api.TestAPI;
import com.diplomna.notification.NotificationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
