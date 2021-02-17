package com.diplomna.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@SuppressWarnings("BusyWait")
@Component
public class UpdateRunner implements CommandLineRunner {

    @Autowired
    private final UpdateService updateService;

    public UpdateRunner(){
        updateService = new UpdateService();
    }

    @Override
    public void run(String... args) throws Exception {
        /*
            update and notification thread
         */
        /*
        while (true) {
            //update all data from API
            updateService.updateAllAssets();
            //check notification status with the new data
            updateService.sendNotifications();
            try {
                //sleep for 1 second
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                //shouldn't get in here
                e.printStackTrace();
            }
        }

         */

        /*
        //free api limitations - 500 requests per month (YahooFinance free subscription sucks)
        //31*24*60*60*1000 - milliseconds in a month = a
        //wait time = a/500 = 5356800 milliseconds to wait between updating all assets ;(

        !не е взето в предвид брой акции за които трябва да бъде извикано api'то, така че
        реално няма да може да се обновява цената толкова пъти месечно,
        но примаме че сървъра няма да си стои пуснат постоянно!
         */

        System.out.println("in update thread");

    }
}
