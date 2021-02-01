package com.diplomna.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationRunner implements CommandLineRunner {

    @Autowired
    private NotificationService notificationService;

    public NotificationRunner(){
        notificationService = new NotificationService();
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        while (true) {
            System.out.println("updating all");
            Thread.sleep(1000);
            System.out.println("all updated");
            Thread.sleep(15000);
        }
         */
        //notificationService.sendNotifications();
        notificationService.sendMail();
    }
}
