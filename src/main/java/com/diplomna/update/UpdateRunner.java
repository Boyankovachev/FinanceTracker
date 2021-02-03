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

        //free api limitations - 500 requests per month (YahooFinance free subscription sucks)
        //31*24*60*60*1000 - milliseconds in a month = a
        //wait time = a/500 = 5356800 milliseconds to wait between updating all assets ;(
        while (true) {
            notificationService.updateAllAssets();
            notificationService.sendNotifications();
            try {
                Thread.sleep(5356800);
            }
            catch (InterruptedException e){
                //shouldn't get in here
                e.printStackTrace();
            }
        }
    }
}
